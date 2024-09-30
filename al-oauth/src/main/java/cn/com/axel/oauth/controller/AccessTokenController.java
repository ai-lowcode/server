package cn.com.axel.oauth.controller;

import cn.com.axel.common.captcha.common.CaptchaConstant;
import cn.com.axel.common.captcha.config.properties.CaptchaProperties;
import cn.com.axel.common.captcha.service.CheckCodeService;
import cn.com.axel.common.core.enums.DeviceType;
import cn.com.axel.common.core.enums.OperateType;
import cn.com.axel.common.core.exception.CaptchaException;
import cn.com.axel.common.core.exception.OAuthValidateException;
import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.log.annotation.Log;
import cn.com.axel.common.oauth.entity.AccessToken;
import cn.com.axel.common.oauth.entity.AuthorizationCode;
import cn.com.axel.common.oauth.entity.RedisAccessToken;
import cn.com.axel.common.oauth.service.SsoUserService;
import cn.com.axel.oauth.cache.redis.UserTokenCache;
import cn.com.axel.oauth.entity.OAuthClient;
import cn.com.axel.oauth.oltu.as.request.OAuthTokenRequest;
import cn.com.axel.oauth.oltu.common.OAuth;
import cn.com.axel.oauth.oltu.common.message.types.GrantType;
import cn.com.axel.oauth.oltu.exception.OAuthProblemException;
import cn.com.axel.oauth.oltu.exception.OAuthSystemException;
import cn.com.axel.oauth.service.LoginService;
import cn.com.axel.oauth.service.OAuth2Service;
import cn.com.axel.oauth.validator.Code2TokenValidator;
import cn.com.axel.oauth.validator.Refresh2TokenValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CompletableFuture;

/**
 * @author: axel
 * @date: 2020/2/17 14:17
 */
@Tag(name = "token获取")
@RestController
@RequestMapping
public class AccessTokenController {
    @Resource
    Code2TokenValidator code2TokenValidator;
    @Resource
    Refresh2TokenValidator refresh2TokenValidator;
    @Resource
    OAuth2Service oAuth2Service;
    @Resource
    SsoUserService ssoUserService;
    @Resource
    LoginService loginService;
    @Resource
    UserTokenCache userTokenCache;
    @Resource
    CheckCodeService checkCodeService;
    @Resource
    CaptchaProperties captchaProperties;

    @Operation(summary = "token获取")
    @PostMapping(value = "/accessToken")
    @Parameters({
            @Parameter(name = OAuth.HeaderType.CONTENT_TYPE, description = "请求类型,必须使用application/x-www-form-urlencoded类型", required = true, in = ParameterIn.HEADER),
            @Parameter(name = OAuth.OAUTH_GRANT_TYPE, description = "token获取类型(authorization_code,password,refresh_token)", required = true),
            @Parameter(name = OAuth.OAUTH_CLIENT_ID, description = "客户端ID 系统默认system", required = true),
            @Parameter(name = OAuth.OAUTH_CLIENT_SECRET, description = "客户端密钥 系统默认system", required = true),
            @Parameter(name = OAuth.OAUTH_REDIRECT_URI, description = "回调地址", required = true),
            @Parameter(name = OAuth.OAUTH_STATE, description = "状态 非必传"),
            @Parameter(name = OAuth.OAUTH_CODE, description = "认证code grant_type=authorization_code时必须"),
            @Parameter(name = OAuth.OAUTH_USERNAME, description = "账号，手机，email grant_type=password时必须"),
            @Parameter(name = OAuth.OAUTH_PASSWORD, description = "密码 grant_type=password时必须"),
            @Parameter(name = OAuth.OAUTH_REFRESH_TOKEN, description = "密码 grant_type=refresh_token时必须"),
            @Parameter(name = CaptchaConstant.CAPTCHA_KEY, description = "验证码key，password方式需要验证码时传入"),
            @Parameter(name = CaptchaConstant.CAPTCHA_VALUE, description = "验证码值，password方式需要验证码时传入")
    })
    @Log(title = "获取token", operateType = OperateType.LOGIN)
    public Result<AccessToken> token(HttpServletRequest request) throws OAuthProblemException, InvocationTargetException, IllegalAccessException, OAuthSystemException {
        OAuthTokenRequest tokenRequest = new OAuthTokenRequest(request);
        Result<OAuthClient> result = code2TokenValidator.validateClient(request, null);
        if (!result.isSuccess()) {
            throw new OAuthValidateException(result.getMsg());
        }
        GrantType grantType = GrantType.valueOf(request.getParameter(OAuth.OAUTH_GRANT_TYPE).toUpperCase());
        RedisAccessToken token = switch (grantType) {
            case AUTHORIZATION_CODE -> code2Token(request, tokenRequest);
            case REFRESH_TOKEN -> refresh2Token(request);
            case PASSWORD -> pwd2Token(request, tokenRequest);
            default -> throw new OAuthValidateException(result.getMsg());
        };
        //缓存用户角色信息
        CompletableFuture.supplyAsync(() -> ssoUserService.getUserInfoAndRoles(token.getUserId(), token.getTenantId()));
        userTokenCache.addUserTokenCache(DeviceType.Web
                , SecurityUtils.getSubject().getSession().getId().toString()
                , token.getUserId(), token.getAccessToken());
        return Result.ok(new AccessToken().setAccess_token(token.getAccessToken()).setExpires_in(token.getExpire()).setRefresh_token(token.getRefreshToken()));
    }

    /**
     * 通过code换取token
     * 此方法首先验证授权码的合法性，验证通过后，使用授权码获取访问令牌
     *
     * @param request HTTP请求对象，用于验证授权码
     * @param tokenRequest 包含授权码等信息的OAuth令牌请求对象
     * @return 返回一个RedisAccessToken对象，包含用户访问系统所需的令牌信息
     * @throws OAuthValidateException 如果授权码验证失败，抛出此异常，包含验证失败的原因
     */
    private RedisAccessToken code2Token(HttpServletRequest request, OAuthTokenRequest tokenRequest) {
        Result<AuthorizationCode> result = code2TokenValidator.validateCode(request, null);
        if (!result.isSuccess()) {
            throw new OAuthValidateException(result.getMsg());
        }
        return oAuth2Service.code2Token(tokenRequest, result.getData());
    }

    /**
     * 通过refreshtoken获取token
     *
     * @param request HTTP请求对象，用于验证授权码
     * @return 返回一个RedisAccessToken对象，包含用户访问系统所需的令牌信息
     */
    private RedisAccessToken refresh2Token(HttpServletRequest request) {
        Result<RedisAccessToken> result = refresh2TokenValidator.validateToken(request, null);
        if (!result.isSuccess()) {
            throw new OAuthValidateException(result.getMsg());
        }
        return oAuth2Service.refresh2Token(result.getData());
    }

    /**
     * 用户名密码直接登录获取token
     *
     * @param request HTTP请求对象，用于验证授权码
     * @param tokenRequest 包含授权码等信息的OAuth令牌请求对象
     * @return 返回一个RedisAccessToken对象，包含用户访问系统所需的令牌信息
     */
    private RedisAccessToken pwd2Token(HttpServletRequest request, OAuthTokenRequest tokenRequest) {
        //password方式请求时，由服务自己校验验证码
        if (captchaProperties.getEnabled()) {
            try {
                checkCodeService.checkCaptcha(request.getParameter(CaptchaConstant.CAPTCHA_VALUE), request.getParameter(CaptchaConstant.CAPTCHA_KEY));
            } catch (CaptchaException e) {
                throw new OAuthValidateException(CaptchaException.Info.getExceptionInfo(e.getMessage()).toString());
            }
        }
        Result<String> result = loginService.login(request);
        if (!result.isSuccess()) {
            throw new OAuthValidateException(result.getMsg());
        }
        return oAuth2Service.buildToken(tokenRequest);
    }

}

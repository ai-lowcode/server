package cn.com.axel.oauth.controller;

import cn.com.axel.common.captcha.common.CaptchaConstant;
import cn.com.axel.common.captcha.config.properties.CaptchaProperties;
import cn.com.axel.common.captcha.service.CheckCodeService;
import cn.com.axel.common.core.constants.ServiceConstants;
import cn.com.axel.common.core.enums.OperateType;
import cn.com.axel.common.core.exception.CaptchaException;
import cn.com.axel.common.core.exception.MyRuntimeException;
import cn.com.axel.common.core.utils.Utils;
import cn.com.axel.common.log.annotation.Log;
import cn.com.axel.common.oauth.common.SerConstant;
import cn.com.axel.common.oauth.entity.AuthorizationCode;
import cn.com.axel.oauth.oltu.as.request.OAuthAuthzRequest;
import cn.com.axel.oauth.oltu.as.response.OAuthASResponse;
import cn.com.axel.oauth.oltu.common.OAuth;
import cn.com.axel.oauth.oltu.common.message.OAuthResponse;
import cn.com.axel.oauth.service.LoginService;
import cn.com.axel.oauth.service.OAuth2Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.text.MessageFormat;
import java.util.function.BiFunction;

/**
 * @author: axel
 * @date: 2020/2/11 11:42
 */
@Tag(name = "认证code获取")
@Controller
@RequestMapping
@Slf4j
public class AuthorizeController {
    private static final String LOGIN_PATH = "login";
    private static final String FORCE_LOGIN = "force_login";
    @Resource
    LoginService loginService;
    @Resource
    OAuth2Service oAuth2Service;
    @Resource
    CheckCodeService checkCodeService;
    @Resource
    CaptchaProperties captchaProperties;

    @Operation(summary = "认证接口")
    @GetMapping("/authorize")
    @Parameters({
            @Parameter(name = OAuth.OAUTH_RESPONSE_TYPE, description = "返回类型", required = true),
            @Parameter(name = OAuth.OAUTH_CLIENT_ID, description = "客户端ID", required = true),
            @Parameter(name = OAuth.OAUTH_REDIRECT_URI, description = "回调地址", required = true),
            @Parameter(name = OAuth.OAUTH_STATE, description = "状态"),
            @Parameter(name = FORCE_LOGIN, description = "强制登录 值为1时强行返回登录界面")
    })
    public Object getAuthorize(Model model, HttpServletRequest request) {
        String force = request.getParameter(FORCE_LOGIN);
        boolean forceLogin = !StringUtils.isEmpty(force) && "1".equals(force);
        return authorize(model, request, (m, r) -> loginService.getLogin(m, r), forceLogin);
    }

    @Operation(summary = "认证接口")
    @PostMapping("/authorize")
    @Parameters({
            @Parameter(name = OAuth.OAUTH_RESPONSE_TYPE, description = "返回类型", required = true),
            @Parameter(name = OAuth.OAUTH_CLIENT_ID, description = "客户端ID", required = true),
            @Parameter(name = OAuth.OAUTH_CLIENT_SECRET, description = "客户端密钥", required = true),
            @Parameter(name = OAuth.OAUTH_REDIRECT_URI, description = "回调地址", required = true),
            @Parameter(name = OAuth.OAUTH_STATE, description = "状态"),
            @Parameter(name = OAuth.OAUTH_USERNAME, description = "账号，手机，email", required = true),
            @Parameter(name = OAuth.OAUTH_PASSWORD, description = "密码", required = true),
            @Parameter(name = SerConstant.REMEMBER_ME, description = "记住我"),
            @Parameter(name = CaptchaConstant.CAPTCHA_KEY, description = "验证码key，单实例服务需要验证码时传入"),
            @Parameter(name = CaptchaConstant.CAPTCHA_VALUE, description = "验证码值，单实例服务需要验证码时传入")
    })
    @Log(title = "code认证接口", operateType = OperateType.LOGIN)
    public Object authorize(Model model, HttpServletRequest request) {
        //单体服务时，自己验证验证码，微服务时由网关验证
        if (ServiceConstants.isBoot(Utils.getServiceType()) && captchaProperties.getEnabled()) {
            try {
                checkCodeService.checkCaptcha(request.getParameter(CaptchaConstant.CAPTCHA_VALUE), request.getParameter(CaptchaConstant.CAPTCHA_KEY));
            } catch (CaptchaException e) {
                model.addAttribute(SerConstant.ERROR_MSG, CaptchaException.Info.getExceptionInfo(e.getMessage()).toString());
                return LOGIN_PATH;
            }
        }
        return authorize(model, request, (m, r) -> loginService.postLogin(m, r), false);
    }

    /**
     * 认证方法
     *
     * @param model    model信息
     * @param request  请求
     * @param function 处理方法get post
     * @return 登录结果
     */
    private Object authorize(Model model, HttpServletRequest request, BiFunction<Model, HttpServletRequest, Boolean> function, boolean forceLogin) {
        OAuthAuthzRequest oauthRequest;
        try {
            oauthRequest = new OAuthAuthzRequest(request);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new MyRuntimeException("错误:请求参数校验出错");
        }
        log.info(MessageFormat.format("用户:{0}登录状态:{1}", SecurityUtils.getSubject().getPrincipal(), SecurityUtils.getSubject().isAuthenticated()));
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            if (!function.apply(model, request)) {
                //登录失败时跳转到登陆页面
                return LOGIN_PATH;
            }
        }
        //如果是强制登录，当前登录状态登出直接返回登录页面
        if (forceLogin) {
            SecurityUtils.getSubject().logout();
            return LOGIN_PATH;
        }
        return buildCodeResponse(request, oauthRequest);

    }

    private ResponseEntity<Object> buildCodeResponse(HttpServletRequest request, OAuthAuthzRequest oauthRequest) {
        AuthorizationCode code = oAuth2Service.buildCode(oauthRequest);
        // 进行OAuth响应构建
        OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_MOVED_TEMPORARILY);
        // 设置授权码
        builder.setCode(code.getCode());
        String state = oauthRequest.getParam(OAuth.OAUTH_STATE);
        if (!StringUtils.isEmpty(state)) {
            builder.setParam(OAuth.OAUTH_STATE, state);
        }
        try {
            // 构建响应
            OAuthResponse response = builder.location(code.getRedirectUri()).buildQueryMessage();
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(new URI(response.getLocationUri()));
            return new ResponseEntity<>(headers, HttpStatus.valueOf(response.getResponseStatus()));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new MyRuntimeException("错误:构建code返回异常");
        }
    }
}

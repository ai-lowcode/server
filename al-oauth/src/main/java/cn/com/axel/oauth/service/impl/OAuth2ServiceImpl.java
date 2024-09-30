package cn.com.axel.oauth.service.impl;

import cn.com.axel.common.core.enums.DeviceType;
import cn.com.axel.common.core.exception.MyRuntimeException;
import cn.com.axel.common.core.utils.ServletUtils;
import cn.com.axel.common.core.utils.StringUtils;
import cn.com.axel.common.core.utils.Utils;
import cn.com.axel.common.oauth.api.vo.TenantVo;
import cn.com.axel.common.oauth.entity.AuthorizationCode;
import cn.com.axel.common.oauth.entity.RedisAccessToken;
import cn.com.axel.common.oauth.entity.SsoUser;
import cn.com.axel.common.oauth.service.SsoUserService;
import cn.com.axel.common.oauth.service.impl.WebTokenServiceImpl;
import cn.com.axel.common.redis.common.RedisPrefix;
import cn.com.axel.oauth.cache.redis.UserTokenCache;
import cn.com.axel.oauth.oltu.as.request.OAuthAuthzRequest;
import cn.com.axel.oauth.oltu.as.request.OAuthRequest;
import cn.com.axel.oauth.oltu.as.request.OAuthTokenRequest;
import cn.com.axel.oauth.oltu.common.OAuth;
import cn.com.axel.oauth.service.OAuth2Service;
import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author: axel
 * @date: 2020/2/15 16:07
 */
@Service
@RefreshScope
public class OAuth2ServiceImpl implements OAuth2Service {

    @Resource
    RedisTemplate<String, Object> redisTemplate;
    @Resource
    SsoUserService ssoUserService;
    @Resource
    WebTokenServiceImpl webTokenService;
    @Resource
    UserTokenCache userTokenCache;

    @Value("${oauth2.expire.code}")
    private long codeExpire = 180;
    @Value("${oauth2.expire.token}")
    private long tokenExpire = 21600;
    @Value("${oauth2.expire.refreshToken}")
    private long reTokenExpire = 604800;

    @Override
    public AuthorizationCode buildCode(OAuthAuthzRequest request) {
        AuthorizationCode code = setProperty(request);
        // 生成授权码
        String authorizationCode = Utils.uuid32();
        code.setCode(authorizationCode);
        code.setState(request.getState());
        setCode(code);
        return code;
    }

    /**
     * 构建设置code,token公共属性
     *
     * @param request 请求参数
     * @return code对象
     */
    private AuthorizationCode setProperty(OAuthRequest request) {
        AuthorizationCode code = new AuthorizationCode();
        code.setClientId(request.getClientId());
        Subject subject = SecurityUtils.getSubject();
        code.setUserId((String) subject.getPrincipal());
        SsoUser user = ssoUserService.getUserById(code.getUserId());
        if (user == null) {
            userTokenCache.delUserTokenCache(DeviceType.Web, subject.getSession().getId().toString(), code.getUserId());
            throw new MyRuntimeException("错误:未获取到用户信息");
        }
        List<TenantVo> tenants = ssoUserService.getUserTenants(code.getUserId());
        if (tenants != null && !tenants.isEmpty()) {
            //设置第一个为默认登录租户
            code.setTenantId(tenants.getFirst().getId());
        }
        code.setAccount(user.getAccount());
        code.setCodeSessionId(subject.getSession().getId().toString());
        code.setScope(StringUtils.join(request.getScopes().iterator(), ","));
        code.setRedirectUri(request.getRedirectURI());
        code.setParentToken(request.getParam(OAuth.OAUTH_ACCESS_TOKEN));
        return code;
    }

    @Override
    public void setCode(AuthorizationCode code) {
        redisTemplate.opsForValue().set(RedisPrefix.buildAuthCodeKey(code.getCode()), code, codeExpire, TimeUnit.SECONDS);
    }

    @Override
    public void delCode(String code) {
        redisTemplate.delete(RedisPrefix.buildAuthCodeKey(code));
    }

    @Override
    public AuthorizationCode getCode(String code) {
        return (AuthorizationCode) redisTemplate.opsForValue().get(RedisPrefix.buildAuthCodeKey(code));
    }

    /**
     * password方式登录 直接构造token
     *
     * @param oAuthTokenRequest 请求
     * @return token对象
     */
    @Override
    public RedisAccessToken buildToken(OAuthTokenRequest oAuthTokenRequest) {
        AuthorizationCode code = setProperty(oAuthTokenRequest);
        return code2Token(oAuthTokenRequest, code);
    }

    @Override
    public RedisAccessToken code2Token(OAuthTokenRequest request, AuthorizationCode code) {
        RedisAccessToken accessToken = new RedisAccessToken();
        BeanUtils.copyProperties(code, accessToken);
        accessToken.setAccessToken(Utils.uuid32());
        accessToken.setRefreshToken(Utils.uuid32());
        accessToken.setTokenSessionId(SecurityUtils.getSubject().getSession().getId().toString());
        accessToken.setGrantType(request.getGrantType());
        accessToken.setClientSecret(request.getClientSecret());
        accessToken.setExpire(tokenExpire);
        accessToken.setReTokenExpire(reTokenExpire);
        accessToken.setIp(Utils.getRemoteIP(Objects.requireNonNull(ServletUtils.getRequest())));
        webTokenService.setToken(accessToken);
        webTokenService.setRefreshToken(accessToken);
        delCode(code.getCode());
        return accessToken;
    }

    @Override
    public RedisAccessToken refresh2Token(RedisAccessToken token) {
        webTokenService.delToken(token.getAccessToken());
        token.setAccessToken(Utils.uuid32());
        token.setIp(Utils.getRemoteIP(Objects.requireNonNull(ServletUtils.getRequest())));
        webTokenService.setToken(token);
        webTokenService.updateRefreshToken(token);
        return token;
    }


}
package cn.com.axel.oauth.service;

import cn.com.axel.common.oauth.entity.AuthorizationCode;
import cn.com.axel.common.oauth.entity.RedisAccessToken;
import cn.com.axel.oauth.oltu.as.request.OAuthAuthzRequest;
import cn.com.axel.oauth.oltu.as.request.OAuthTokenRequest;

/**
 * @author: axel
 * @date: 2020/2/13 12:51
 */
public interface OAuth2Service {
    AuthorizationCode buildCode(OAuthAuthzRequest request);

    void setCode(AuthorizationCode code);

    void delCode(String code);

    AuthorizationCode getCode(String code);

    RedisAccessToken buildToken(OAuthTokenRequest request);

    RedisAccessToken code2Token(OAuthTokenRequest request, AuthorizationCode code);

    RedisAccessToken refresh2Token(RedisAccessToken token);

}

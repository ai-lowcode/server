package cn.com.axel.oauth.realm;

import cn.com.axel.common.oauth.common.SerConstant;
import cn.com.axel.common.core.exception.OAuthValidateException;
import cn.com.axel.common.oauth.entity.SsoUser;
import cn.com.axel.oauth.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;

import jakarta.annotation.Resource;


/**
 * 微信手机号登陆验证
 *
 * @author: axel
 * @date: 2021/10/26 15:41
 */
public class WxPhoneRealm extends BaseRealm {
    @Resource
    LoginService loginService;

    @Override
    protected AuthenticationInfo buildAuthenticationInfo(SsoUser user, AuthenticationToken authenticationToken, boolean newUser) {
        String sessionKey = String.valueOf((char[]) authenticationToken.getCredentials());
        String openId = loginService.getOpenIdBySessionKey(sessionKey);
        if (StringUtils.isEmpty(openId)) {
            throw new OAuthValidateException(SerConstant.INVALID_WX_ID_DESCRIPTION);
        }
        return new SimpleAuthenticationInfo(
                user.getId(), sessionKey, getName());
    }
}

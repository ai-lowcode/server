package cn.com.axel.oauth.credentials;

import cn.com.axel.oauth.common.MyUsernamePasswordToken;
import cn.com.axel.oauth.service.LoginService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;

import jakarta.annotation.Resource;


/**
 * @author: axel
 * @date: 2020/2/25 16:51
 */
public class SmsCredentialsMatcher extends AutoUserCredentialsMatcher {
    @Resource
    LoginService loginService;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        MyUsernamePasswordToken myToken = (MyUsernamePasswordToken) authenticationToken;
        boolean matches = super.doCredentialsMatch(authenticationToken, authenticationInfo);
        if (matches) {
            insertNewUser(myToken.isNew(), myToken.getUserInfo());
        }
        boolean success = loginService.retryLimit(myToken.getUserInfo().getId(), matches);
        if (success) {
            loginService.delSmsCode(myToken.getUsername());
        }
        return success;
    }
}

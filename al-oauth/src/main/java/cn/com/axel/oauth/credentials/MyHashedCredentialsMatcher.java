package cn.com.axel.oauth.credentials;

import cn.com.axel.oauth.common.MyUsernamePasswordToken;
import cn.com.axel.oauth.service.LoginService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

import jakarta.annotation.Resource;

/**
 * @author: axel
 * @date: 2020/2/10 19:48
 */
public class MyHashedCredentialsMatcher extends HashedCredentialsMatcher {
    @Resource
    LoginService loginService;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        MyUsernamePasswordToken myToken = (MyUsernamePasswordToken) token;
        boolean matches = super.doCredentialsMatch(token, info);
        return loginService.retryLimit(myToken.getUserInfo().getId(), matches);
    }

}

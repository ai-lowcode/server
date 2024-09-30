package cn.com.axel.oauth.credentials;

import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.oauth.common.SerConstant;
import cn.com.axel.common.core.exception.OAuthValidateException;
import cn.com.axel.common.oauth.entity.SsoUser;
import cn.com.axel.common.oauth.service.SsoUserService;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import jakarta.annotation.Resource;

/**
 * 不存在的用户自动创建用户
 *
 * @author: axel
 * @date: 2021/10/26 17:57
 */
public class AutoUserCredentialsMatcher extends SimpleCredentialsMatcher {
    @Resource
    SsoUserService ssoUserService;

    protected void insertNewUser(boolean newUser, SsoUser user) {
        if (newUser) {
            Result<SsoUser> result = ssoUserService.insertUser(user);
            if (!result.isSuccess()) {
                throw new OAuthValidateException(SerConstant.INVALID_NEW_USER_DESCRIPTION);
            }
        }
    }
}

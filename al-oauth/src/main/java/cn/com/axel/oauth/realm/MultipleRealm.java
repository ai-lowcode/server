package cn.com.axel.oauth.realm;

import cn.com.axel.oauth.common.MyUsernamePasswordToken;
import cn.com.axel.common.oauth.common.SerConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.AuthorizingRealm;

import java.util.Map;

/**
 * @author: axel
 * @date: 2020/2/10 19:21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MultipleRealm extends ModularRealmAuthenticator {
    private Map<SerConstant.LoginType, AuthorizingRealm> myRealms;

    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        MyUsernamePasswordToken token = (MyUsernamePasswordToken) authenticationToken;
        return super.doSingleRealmAuthentication(getMyRealms().get(token.getLoginType()), authenticationToken);
    }
}

package cn.com.axel.oauth.realm;

import cn.com.axel.oauth.common.MyUsernamePasswordToken;
import cn.com.axel.common.oauth.common.SerConstant;
import cn.com.axel.oauth.entity.RedisQrCode;
import cn.com.axel.common.oauth.entity.SsoUser;
import cn.com.axel.oauth.service.QRCodeService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;

import jakarta.annotation.Resource;

/**
 * @author: axel
 * @date: 2020/2/11 9:42
 */
public class QRCodeRealm extends BaseRealm {
    @Resource
    QRCodeService qrCodeService;

    @Override
    protected AuthenticationInfo buildAuthenticationInfo(SsoUser user, AuthenticationToken authenticationToken, boolean newUser) {
        String[] secret = StringUtils.split(String.valueOf(((MyUsernamePasswordToken) authenticationToken).getPassword()), ",");
        //secret由扫码code值和secret拼接而成
        if (secret == null || secret.length != 2) {
            throw new IncorrectCredentialsException(SerConstant.INVALID_USER_SECRET_DESCRIPTION);
        }
        RedisQrCode redisQrCode = qrCodeService.checkQRCode(secret[0]);
        if (redisQrCode == null) {
            throw new IncorrectCredentialsException(SerConstant.INVALID_USER_SECRET_DESCRIPTION);
        }
        return new SimpleAuthenticationInfo(
                user.getId(), redisQrCode.getCode() + "," + redisQrCode.getSecret(), getName());
    }
}

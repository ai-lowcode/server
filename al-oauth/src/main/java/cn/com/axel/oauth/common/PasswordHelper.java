package cn.com.axel.oauth.common;

import cn.com.axel.oauth.config.properties.ShiroProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.lang.util.ByteSource;
import org.springframework.stereotype.Component;

/**
 * @author: axel
 * @date: 2020/2/13 16:32
 */
@Schema(description = "密码助手，用于新增用户时密码加密")
@Component
public class PasswordHelper {
    /**
     * 生成hash盐
     *
     * @return 盐值
     */
    public static String buildSalt() {
        return new SecureRandomNumberGenerator().nextBytes().toHex();
    }

    /**
     * 密码通过shiro配置的加密模式和hash次数进行加密
     *
     * @param userId 用户id
     * @param password 密码
     * @param salt 盐
     * @return 加密后的密码
     */
    public String encryptPassword(String userId, String password, String salt) {
        if (StringUtils.isEmpty(password)) {
            return null;
        }
        SimpleHash simpleHash = new SimpleHash(
                ShiroProperties.algorithmName,
                password,
                ByteSource.Util.bytes(userId + salt),
                ShiroProperties.hashIterations);
        if (ShiroProperties.hexEncoded) {
            return simpleHash.toHex();
        }
        return simpleHash.toBase64();

    }

//    public static void main(String[] args){
//        System.out.println(buildSalt());
//    }
}

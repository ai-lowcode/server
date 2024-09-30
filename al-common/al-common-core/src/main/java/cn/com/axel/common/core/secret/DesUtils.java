package cn.com.axel.common.core.secret;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @description: DES加解密
 * @author: axel
 * @date: 2023/3/16
 */
public class DesUtils {
    private static final Key key;
    private static final String KEY_STR = "myKey";
    private static final String CHARSET_NAME = "UTF-8";
    private static final String ALGORITHM = "DES";

    static {
        try {
            //生成DES算法对象
            KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
            //运用SHA1安全策略
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            //设置上密钥种子
            secureRandom.setSeed(KEY_STR.getBytes());
            //初始化基于SHA1的算法对象
            generator.init(secureRandom);
            //生成密钥对象
            key = generator.generateKey();
            generator = null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 获取加密的信息
     * @param str 待加密的字符串
     * @return 加密后的字符串
     */
    public static String encrypt(String str) {
        //基于BASE64编码，接收byte[]并转换成String
        try {
            //按utf8编码
            byte[] bytes = str.getBytes(CHARSET_NAME);
            //获取加密对象
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            //初始化密码信息
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //加密
            byte[] doFinal = cipher.doFinal(bytes);
            //byte[]to encode好的String 并返回
            return new String(Base64.getEncoder().encode(doFinal));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /***
     * 获取解密之后的信息
     * @param str 需要解密的字符串
     * @return 解密后的字符串
     */
    public static String decrypt(String str) {
        try {
            //将字符串decode成byte[]
            byte[] bytes = Base64.getDecoder().decode(str);
            //获取解密对象
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            //初始化解密信息
            cipher.init(Cipher.DECRYPT_MODE, key);
            //解密
            byte[] doFinal = cipher.doFinal(bytes);
            return new String(doFinal, CHARSET_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String mFish = encrypt("axel");
        System.out.println(mFish);
        System.out.println(decrypt(mFish));
    }
}

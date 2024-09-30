package cn.com.axel.oauth.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: axel
 * @date: 2020/3/5 17:11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RedisQrCode extends QRCode {
    private String accessToken;
}

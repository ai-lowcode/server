package cn.com.axel.oauth.service;

import cn.com.axel.oauth.entity.RedisQrCode;

/**
 * @author: axel
 * @date: 2020/3/5 14:57
 */
public interface QRCodeService {
    void saveQRCode(RedisQrCode qrCode);

    RedisQrCode checkQRCode(String code);

    void updateQRCode(RedisQrCode qrCode);
}

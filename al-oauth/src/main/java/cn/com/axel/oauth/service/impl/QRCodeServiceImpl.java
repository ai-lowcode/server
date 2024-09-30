package cn.com.axel.oauth.service.impl;

import cn.com.axel.common.redis.common.RedisPrefix;
import cn.com.axel.oauth.entity.RedisQrCode;
import cn.com.axel.oauth.service.QRCodeService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

import java.util.concurrent.TimeUnit;

/**
 * @author: axel
 * @date: 2020/3/5 14:59
 */
@Service
public class QRCodeServiceImpl implements QRCodeService {
    @Resource
    RedisTemplate<String, Object> redisTemplate;

    //qrcode库中存储时间
    private static final long qrCodeExpire = 5;

    @Override
    public void saveQRCode(RedisQrCode qrCode) {
        redisTemplate.opsForValue().set(RedisPrefix.buildQrCodeKey(qrCode.getCode()), qrCode, qrCodeExpire, TimeUnit.MINUTES);
    }

    @Override
    public RedisQrCode checkQRCode(String code) {
        return (RedisQrCode) redisTemplate.opsForValue().get(RedisPrefix.buildQrCodeKey(code));
    }

    @Override
    public void updateQRCode(RedisQrCode qrCode) {
        String key = RedisPrefix.buildQrCodeKey(qrCode.getCode());
        Long expire = redisTemplate.getExpire(key);
        assert expire != null;
        redisTemplate.opsForValue().set(key, qrCode, expire, TimeUnit.SECONDS);
    }
}

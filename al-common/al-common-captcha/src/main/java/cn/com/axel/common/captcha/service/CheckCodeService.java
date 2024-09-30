package cn.com.axel.common.captcha.service;

import cn.com.axel.common.core.exception.CaptchaException;
import cn.com.axel.common.core.web.Result;

import java.util.Map;

/**
 * @author: axel
 * @date: 2021/8/12 11:06
 */
public interface CheckCodeService {

    /**
     * 创建验证码
     *
     * @return Map
     */
    Result<Map<String, Object>> createCaptcha();

    /**
     * 校验验证码
     *
     * @param key   key
     * @param value 值
     * @throws CaptchaException 异常
     */
    void checkCaptcha(String key, String value) throws CaptchaException;
}

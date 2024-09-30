package cn.com.axel.common.oauth.validator;

import cn.com.axel.common.core.utils.AuthInfoUtils;
import cn.com.axel.common.core.utils.StringUtils;
import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.oauth.common.SerConstant;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * @description: 统一token校验
 * @author: axel
 * @date: 2023/1/29 23:28
 */
@Component
public class TokenValidator {
    @Resource
    private WebTokenValidator webTokenValidator;
    @Resource
    private WeChatTokenValidator weChatTokenValidator;

    public <R> Result<?> validator(R request) {
        String accessToken = AuthInfoUtils.getAccessToken(request);
        Result<?> result;
        if (!StringUtils.isEmpty(accessToken) && accessToken.startsWith(SerConstant.WX_PREFIX)) {
            result = weChatTokenValidator.validate(accessToken);
        } else {
            result = webTokenValidator.validate(accessToken);
        }
        return result;
    }
}

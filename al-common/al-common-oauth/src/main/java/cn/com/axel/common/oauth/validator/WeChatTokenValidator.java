package cn.com.axel.common.oauth.validator;


import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.oauth.entity.WeChatToken;
import cn.com.axel.common.oauth.service.impl.WeChatTokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author: axel
 * @description: 微信请求token校验
 * @date: 2021/12/14 9:33
 */
@Component
public class WeChatTokenValidator extends AbstractTokenValidator<WeChatToken> {

    public WeChatTokenValidator(@Autowired WeChatTokenServiceImpl weChatTokenService) {
        super(weChatTokenService);
    }

    public Result<WeChatToken> validate(HttpServletRequest request) {
        return validateT(request);
    }

    @Override
    public Result<WeChatToken> validate(HttpServletRequest request, Result<WeChatToken> result) {
        return validateT(request);
    }
}

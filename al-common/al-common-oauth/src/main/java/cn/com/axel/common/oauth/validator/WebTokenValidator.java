package cn.com.axel.common.oauth.validator;

import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.oauth.entity.RedisAccessToken;
import cn.com.axel.common.oauth.service.impl.WebTokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author: axel
 * @date: 2020/2/17 19:06
 */
@Component
public class WebTokenValidator extends AbstractTokenValidator<RedisAccessToken> {

    public WebTokenValidator(@Autowired WebTokenServiceImpl webTokenService) {
        super(webTokenService);
    }

    public Result<RedisAccessToken> validate(ServerHttpRequest request) {
        return validateT(request);
    }

    public Result<RedisAccessToken> validate(HttpServletRequest request) {
        return validateT(request);
    }

    @Override
    public Result<RedisAccessToken> validate(HttpServletRequest request, Result<RedisAccessToken> result) {
        return validateT(request);
    }

}

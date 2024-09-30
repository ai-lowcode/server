package cn.com.axel.oauth.validator;

import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.oauth.entity.RedisAccessToken;
import cn.com.axel.common.oauth.service.impl.WebTokenServiceImpl;
import cn.com.axel.common.oauth.validator.IBaseValidator;
import cn.com.axel.oauth.oltu.common.OAuth;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

/**
 * @author: axel
 * @date: 2020/2/18 18:53
 */
public abstract class AbstractRefreshTokenValidator implements IBaseValidator<RedisAccessToken> {
    @Resource
    WebTokenServiceImpl webTokenService;

    public Result<RedisAccessToken> getRefreshToken(HttpServletRequest request, Result<RedisAccessToken> result) {
        RedisAccessToken token;
        if (result == null || result.getData() == null) {
            result = new Result<>();
            String refreshToken = request.getParameter(OAuth.OAUTH_REFRESH_TOKEN);
            if (StringUtils.isEmpty(refreshToken)) {
                return result.setSuccess(false).setMsg("错误:token不正确");
            }
            token = webTokenService.getRefreshToken(refreshToken);
            if (token == null) {
                return result.setSuccess(false).setMsg("错误:token不正确");
            }
            return result.setData(token);
        }
        return result;
    }
}

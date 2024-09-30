package cn.com.axel.common.oauth.validator;

import cn.com.axel.common.core.utils.AuthInfoUtils;
import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.oauth.service.TokenService;
import org.apache.commons.lang3.StringUtils;

/**
 * @description: 抽象token校验
 * @author: axel
 * @date: 2023/1/7 15:25
 */
public abstract class AbstractTokenValidator<T> implements IBaseValidator<T> {
    TokenService<?> tokenService;

    public AbstractTokenValidator(TokenService<?> tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * 校验不同类型request中包含的token信息是否正确
     *
     * @param request 请求参数
     * @param <R>     泛型
     * @return 返回校验结果
     */
    public <R> Result<T> validateT(R request) {
        String accessToken = AuthInfoUtils.getAccessToken(request);
        return validate(accessToken);

    }

    /**
     * 已获取到token，直接校验
     *
     * @param accessToken token
     * @return 返回校验结果
     */
    @SuppressWarnings("unchecked")
    public Result<T> validate(String accessToken) {
        T token;
        if (StringUtils.isEmpty(accessToken)) {
            Result.fail("错误:令牌Token不允许为空");
        }
        token = (T) tokenService.getToken(accessToken);
        if (token == null) {
            return Result.fail("错误:登陆已过期或令牌不存在");
        }
        return Result.ok(token);
    }
}

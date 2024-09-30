package cn.com.axel.oauth.validator;

import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.oauth.entity.RedisAccessToken;
import cn.com.axel.common.oauth.validator.IBaseValidator;
import cn.com.axel.oauth.oltu.common.OAuth;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: axel
 * @date: 2020/2/18 19:02
 */
@Component
public class Refresh2TokenValidator extends MultipleValidator {
    List<Class<? extends IBaseValidator<RedisAccessToken>>> validateTokenList = new ArrayList<>();

    public Refresh2TokenValidator() {
        this.validateClientList.add(ClientIdExistValidator.class);
        this.validateClientList.add(ClientSecretExistValidator.class);
        this.validateClientList.add(GrantTypeExistValidator.class);
        this.validateClientList.add(RedirectUriExistValidator.class);
        this.validateTokenList.add(UriEqualValidator.class);
        this.validateTokenList.add(ClientIdEqualValidator.class);
        this.validateTokenList.add(ClientSecretEqualValidator.class);
    }

    /**
     * token 参数相关的组合校验
     *
     * @param request HTTP请求对象，用于获取授权码
     * @param result 前一次校验结果对象，如果为null，则需要在方法内部新建
     * @return 返回封装了AuthorizationCode的Result对象，如果授权码无效，则返回错误信息
     */
    public Result<RedisAccessToken> validateToken(HttpServletRequest request, Result<RedisAccessToken> result) {
        return validate(request, result, validateTokenList);
    }

    /**
     * 校验refreshToken与之前获取token时传入的clientId是否一致
     */
    @Component
    public static class ClientIdEqualValidator extends AbstractRefreshTokenValidator {
        @Override
        public Result<RedisAccessToken> validate(HttpServletRequest request, Result<RedisAccessToken> result) {
            Result<RedisAccessToken> result1 = getRefreshToken(request, result);
            if (!result1.isSuccess()) {
                return result1;
            }
            String clientId = request.getParameter(OAuth.OAUTH_CLIENT_ID);
            if (!StringUtils.isEmpty(clientId) && clientId.equals(result1.getData().getClientId())) {
                return result1;
            }
            return result1.setSuccess(false).setMsg("错误:token和refreshToken两次传入的clientId不一致");
        }
    }

    /**
     * 校验refreshToken与之前获取token时传入的clientSecret是否一致
     */
    @Component
    public static class ClientSecretEqualValidator extends AbstractRefreshTokenValidator {
        @Override
        public Result<RedisAccessToken> validate(HttpServletRequest request, Result<RedisAccessToken> result) {
            Result<RedisAccessToken> result1 = getRefreshToken(request, result);
            if (!result1.isSuccess()) {
                return result1;
            }
            String secret = request.getParameter(OAuth.OAUTH_CLIENT_SECRET);
            if (!StringUtils.isEmpty(secret) && secret.equals(result1.getData().getClientSecret())) {
                return result1;
            }
            return result1.setSuccess(false).setMsg("错误:token和refreshToken两次传入的clientSecret不一致");
        }
    }

    /**
     * 校验refreshToken与之前获取token时传入的uri是否一致
     */
    @Component
    public static class UriEqualValidator extends AbstractRefreshTokenValidator {
        @Override
        public Result<RedisAccessToken> validate(HttpServletRequest request, Result<RedisAccessToken> result) {
            Result<RedisAccessToken> result1 = getRefreshToken(request, result);
            if (!result1.isSuccess()) {
                return result1;
            }
            String uri = request.getParameter(OAuth.OAUTH_REDIRECT_URI);
            if (!StringUtils.isEmpty(uri) && uri.equals(result1.getData().getRedirectUri())) {
                return result1;
            }
            return result1.setSuccess(false).setMsg("错误:token和refreshToken两次传入的uri不一致");
        }
    }
}

package cn.com.axel.oauth.validator;

import cn.com.axel.common.core.web.Result;
import cn.com.axel.oauth.entity.OAuthClient;
import cn.com.axel.oauth.oltu.common.OAuth;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author: axel
 * @date: 2020/2/17 15:26
 */
@Component
public class ClientSecretExistValidator extends AbstractClientValidator {
    @Override
    public Result<OAuthClient> validate(HttpServletRequest request, Result<OAuthClient> result) {
        Result<OAuthClient> result1 = getOAuthClient(request, result);
        if (!result1.isSuccess()) {
            return result1;
        }
        String secret = request.getParameter(OAuth.OAUTH_CLIENT_SECRET);
        if (!StringUtils.isEmpty(secret) && secret.equals(result1.getData().getClientSecret())) {
            return result1;
        }
        return result1.setSuccess(false).setMsg("错误:客户端密钥错误!");
    }
}

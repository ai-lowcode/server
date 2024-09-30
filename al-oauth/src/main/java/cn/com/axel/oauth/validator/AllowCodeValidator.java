package cn.com.axel.oauth.validator;

import cn.com.axel.common.core.web.Result;
import cn.com.axel.oauth.entity.OAuthClient;
import cn.com.axel.oauth.oltu.common.message.types.GrantType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
 * @author: axel
 * @date: 2020/2/16 18:57
 */
@Component
public class AllowCodeValidator extends AbstractClientValidator {
    @Override
    public Result<OAuthClient> validate(HttpServletRequest request, Result<OAuthClient> result) {
        Result<OAuthClient> result1 = getOAuthClient(request, result);
        if (!result1.isSuccess()) {
            return result1;
        }
        if (!result1.getData().getGrantTypes().contains(GrantType.AUTHORIZATION_CODE.toString())) {
            return result1.setSuccess(false).setMsg("错误:该客户端不支持code请求方式！");
        }
        return result1;
    }
}

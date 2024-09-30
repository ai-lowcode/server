package cn.com.axel.oauth.validator;

import cn.com.axel.common.core.web.Result;
import cn.com.axel.oauth.entity.OAuthClient;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author: axel
 * @date: 2020/2/13 13:58
 */
@Component
public class ClientIdExistValidator extends AbstractClientValidator {

    @Override
    public Result<OAuthClient> validate(HttpServletRequest request, Result<OAuthClient> result) {
        return getOAuthClient(request, result);
    }
}

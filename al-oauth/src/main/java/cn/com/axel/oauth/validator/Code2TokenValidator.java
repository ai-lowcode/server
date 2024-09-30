package cn.com.axel.oauth.validator;

import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.oauth.entity.AuthorizationCode;
import cn.com.axel.common.oauth.validator.IBaseValidator;
import cn.com.axel.oauth.oltu.common.OAuth;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: axel
 * @date: 2020/2/17 14:43
 */
@Component
public class Code2TokenValidator extends MultipleValidator {
    List<Class<? extends IBaseValidator<AuthorizationCode>>> validateCodeList = new ArrayList<>();

    public Code2TokenValidator() {
        this.validateClientList.add(ClientIdExistValidator.class);
        this.validateClientList.add(ClientSecretExistValidator.class);
        this.validateClientList.add(GrantTypeExistValidator.class);
        this.validateClientList.add(RedirectUriExistValidator.class);
        this.validateCodeList.add(ClientIdEqualValidator.class);
        this.validateCodeList.add(UriEqualValidator.class);
    }

    /**
     * code参数相关多个校验组合
     *
     * @param request HTTP请求对象，用于获取授权码
     * @param result 前一次校验结果对象，如果为null，则需要在方法内部新建
     * @return 返回封装了AuthorizationCode的Result对象，如果授权码无效，则返回错误信息
     */
    public Result<AuthorizationCode> validateCode(HttpServletRequest request, Result<AuthorizationCode> result) {
        return validate(request, result, validateCodeList);
    }

    /**
     * 校验code换token两次传入的clientId是否一致
     */
    @Component
    public static class ClientIdEqualValidator extends AbstractCodeValidator {
        @Override
        public Result<AuthorizationCode> validate(HttpServletRequest request, Result<AuthorizationCode> result) {
            Result<AuthorizationCode> result1 = getAuthCode(request, result);
            if (!result1.isSuccess()) {
                return result1;
            }
            String clientId = request.getParameter(OAuth.OAUTH_CLIENT_ID);
            if (!StringUtils.isEmpty(clientId) && clientId.equals(result1.getData().getClientId())) {
                return result1;
            }
            return result1.setSuccess(false).setMsg("错误:获取code和token两次传入的clientId不一致");
        }
    }

    /**
     * 校验code换token两次传入的uri是否一致
     */
    @Component
    public static class UriEqualValidator extends AbstractCodeValidator {
        @Override
        public Result<AuthorizationCode> validate(HttpServletRequest request, Result<AuthorizationCode> result) {
            Result<AuthorizationCode> result1 = getAuthCode(request, result);
            if (!result1.isSuccess()) {
                return result1;
            }
            String uri = request.getParameter(OAuth.OAUTH_REDIRECT_URI);
            if (!StringUtils.isEmpty(uri) && uri.equals(result1.getData().getRedirectUri())) {
                return result1;
            }
            return result1.setSuccess(false).setMsg("错误:获取code和token两次传入的uri不一致");
        }
    }
}

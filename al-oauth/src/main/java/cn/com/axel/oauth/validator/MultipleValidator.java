package cn.com.axel.oauth.validator;

import cn.com.axel.common.core.utils.SpringBeanFactory;
import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.oauth.validator.IBaseValidator;
import cn.com.axel.oauth.entity.OAuthClient;
import lombok.Data;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: axel
 * @date: 2020/2/13 14:41
 */
@Data
public abstract class MultipleValidator {

    List<Class<? extends IBaseValidator<OAuthClient>>> validateClientList = new ArrayList<>();

    /**
     * 客户端参数相关多个校验
     *
     * @param request HTTP请求对象，用于获取授权码
     * @param result 前一次校验结果对象，如果为null，则需要在方法内部新建
     * @return 返回封装了AuthorizationCode的Result对象，如果授权码无效，则返回错误信息
     */
    public Result<OAuthClient> validateClient(HttpServletRequest request, Result<OAuthClient> result) {
        return validate(request, result, validateClientList);
    }

    public <T> Result<T> validate(HttpServletRequest request, Result<T> result, List<Class<? extends IBaseValidator<T>>> list) {
        for (Class<? extends IBaseValidator<T>> validator : list) {
            result = SpringBeanFactory.getBean(validator)
                    .validate(request, result);
            if (!result.isSuccess()) {
                return result;
            }
        }
        return result;
    }
}

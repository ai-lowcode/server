package cn.com.axel.common.oauth.validator;

import cn.com.axel.common.core.web.Result;
import jakarta.servlet.http.HttpServletRequest;


/**
 * @author: axel
 * @date: 2020/2/13 13:43
 */
public interface IBaseValidator<T> {
    Result<T> validate(HttpServletRequest request, Result<T> result);
}

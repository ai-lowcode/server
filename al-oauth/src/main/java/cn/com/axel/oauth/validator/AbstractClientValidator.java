package cn.com.axel.oauth.validator;

import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.oauth.validator.IBaseValidator;
import cn.com.axel.oauth.cache.temp.ClientTempCache;
import cn.com.axel.oauth.entity.OAuthClient;
import cn.com.axel.oauth.oltu.common.OAuth;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author: axel
 * @date: 2020/2/16 18:10
 */
public abstract class AbstractClientValidator implements IBaseValidator<OAuthClient> {

    @Resource
    ClientTempCache clientTempCache;

    /**
     * 获取客户端信息
     * 如果参数中已传入客户端信息直接返回，如果未传入从redis或数据库中获取
     *
     * @param request 请求
     * @param result 上次校验结果
     * @return 返回客户端信息
     */
    public Result<OAuthClient> getOAuthClient(HttpServletRequest request, Result<OAuthClient> result) {
        OAuthClient client;
        if (result == null || result.getData() == null) {
            result = new Result<>();
            String clientId = request.getParameter(OAuth.OAUTH_CLIENT_ID);
            client = clientTempCache.getFromCacheAndDB(clientId);
            if (client == null) {
                return result.setSuccess(false).setMsg("错误:校验客户端ID失败!");
            }
            result.setData(client);
        }
        return result;
    }
}

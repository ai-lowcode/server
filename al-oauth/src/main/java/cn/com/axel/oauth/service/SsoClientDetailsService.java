package cn.com.axel.oauth.service;

import cn.com.axel.common.core.web.Result;
import cn.com.axel.oauth.entity.SsoClientDetails;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description: 客户端信息
 * @author: axel
 * @date: 2023-05-12
 * @version: V0.0.1
 */
public interface SsoClientDetailsService extends IService<SsoClientDetails> {

    Result<SsoClientDetails> updateClient(SsoClientDetails ssoClientDetails);

    void removeClientCache(String clientId);
}

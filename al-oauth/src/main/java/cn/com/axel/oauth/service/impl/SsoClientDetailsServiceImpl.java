package cn.com.axel.oauth.service.impl;

import cn.com.axel.common.core.utils.StringUtils;
import cn.com.axel.common.core.web.Result;
import cn.com.axel.oauth.cache.temp.ClientTempCache;
import cn.com.axel.oauth.entity.SsoClientDetails;
import cn.com.axel.oauth.mapper.SsoClientDetailsMapper;
import cn.com.axel.oauth.service.SsoClientDetailsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: 客户端信息
 * @author: axel
 * @date: 2023-05-12
 * @version: V1.3.1
 */
@Service
public class SsoClientDetailsServiceImpl extends ServiceImpl<SsoClientDetailsMapper, SsoClientDetails> implements SsoClientDetailsService {
    @Resource
    ClientTempCache clientTempCache;

    @Override
    @Transactional
    public Result<SsoClientDetails> updateClient(SsoClientDetails ssoClientDetails) {
        if (baseMapper.updateById(ssoClientDetails) > 0) {
            if (StringUtils.isEmpty(ssoClientDetails.getClientId())) {
                ssoClientDetails = baseMapper.selectById(ssoClientDetails.getId());
            }
            removeClientCache(ssoClientDetails.getClientId());
            return Result.ok(ssoClientDetails, "客户端信息-编辑成功!");
        }
        return Result.fail(ssoClientDetails, "错误:客户端信息-编辑失败!");
    }

    @Override
    public void removeClientCache(String clientId) {
        clientTempCache.removeOneCache(clientId);
    }
}

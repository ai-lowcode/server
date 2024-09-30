package cn.com.axel.oauth.cache.temp;

import cn.com.axel.common.oauth.api.vo.TenantVo;
import cn.com.axel.common.redis.common.RedisPrefix;
import cn.com.axel.common.redis.temp.BaseTempCache;
import cn.com.axel.oauth.mapper.SsoUserMapper;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

import java.util.List;

/**
 * @description: 用户租户缓存
 * @author: axel
 * @date: 2023/6/25
 */
@Component
public class UserTenantTempCache extends BaseTempCache<List<TenantVo>> {
    @Resource
    SsoUserMapper ssoUserMapper;

    @Override
    protected String buildKey(String... key) {
        return RedisPrefix.buildUser2TenantsKey(key[0]);
    }

    @Override
    protected List<TenantVo> getFromDB(String... key) {
        return ssoUserMapper.getUserTenants(key[0]);
    }

}
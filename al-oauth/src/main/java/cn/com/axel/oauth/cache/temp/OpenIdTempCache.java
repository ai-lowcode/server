package cn.com.axel.oauth.cache.temp;

import cn.com.axel.common.redis.temp.BaseTempCache;
import cn.com.axel.common.redis.common.RedisPrefix;
import cn.com.axel.oauth.mapper.SsoUserMapper;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * @author: axel
 * @date: 2020/2/29 15:07
 */
@Component
public class OpenIdTempCache extends BaseTempCache<String> {
    @Resource
    SsoUserMapper ssoUserMapper;

    @Override
    protected String buildKey(String... key) {
        return RedisPrefix.buildOpenId2userIdKey(key[0]);
    }

    @Override
    protected String getFromDB(String... key) {
        return ssoUserMapper.getUserIdByOpenId(key[0]);
    }
}

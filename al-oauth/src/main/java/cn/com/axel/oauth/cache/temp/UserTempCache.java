package cn.com.axel.oauth.cache.temp;

import cn.com.axel.common.redis.temp.BaseTempCache;
import cn.com.axel.oauth.mapper.SsoUserMapper;
import cn.com.axel.common.oauth.entity.SsoUser;
import cn.com.axel.common.redis.common.RedisPrefix;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * @author: axel
 * @date: 2020/2/14 17:46
 */
@Component
public class UserTempCache extends BaseTempCache<SsoUser> {
    @Resource
    SsoUserMapper ssoUserMapper;

    @Override
    protected String buildKey(String... key) {
        return RedisPrefix.buildUserDetailKey(key[0]);
    }

    @Override
    protected SsoUser getFromDB(String... key) {
        return ssoUserMapper.getUserById(key[0], null);
    }

}

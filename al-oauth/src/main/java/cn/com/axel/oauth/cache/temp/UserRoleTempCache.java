package cn.com.axel.oauth.cache.temp;

import cn.com.axel.common.oauth.api.entity.UserRole;
import cn.com.axel.common.redis.common.RedisPrefix;
import cn.com.axel.common.redis.temp.BaseTempCache;
import cn.com.axel.oauth.mapper.SsoUserMapper;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

import java.util.List;

/**
 * @author: axel
 * @description: 用户角色临时缓存
 * @date: 2022/12/5 22:00
 */
@Component("userRoleTempCache")
public class UserRoleTempCache extends BaseTempCache<List<UserRole>> {
    @Resource
    SsoUserMapper ssoUserMapper;

    /**
     * key [0] userId [1] tenantId
     *
     * @param key key
     * @return key
     */
    @Override
    protected String buildKey(String... key) {
        return RedisPrefix.buildUser2RolesKey(key[0], key[1]);
    }

    /**
     * key [0] userId [1] tenantId
     *
     * @param key key
     * @return 数据
     */
    @Override
    protected List<UserRole> getFromDB(String... key) {
        return ssoUserMapper.getUserRoles(key[0], key[1]);
    }
}

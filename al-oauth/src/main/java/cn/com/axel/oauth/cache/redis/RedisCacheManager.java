package cn.com.axel.oauth.cache.redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * @author: axel
 * @date: 2020/2/11 17:53
 */
@Component
public class RedisCacheManager implements CacheManager {

    @Resource
    private RedisCache cache;

    @Override
    @SuppressWarnings("unchecked")
    public Cache<String, Object> getCache(String name) throws CacheException {
        return cache;
    }
}

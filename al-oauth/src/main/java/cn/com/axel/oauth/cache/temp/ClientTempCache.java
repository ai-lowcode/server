package cn.com.axel.oauth.cache.temp;

import cn.com.axel.common.redis.temp.BaseTempCache;
import cn.com.axel.common.redis.common.RedisPrefix;
import cn.com.axel.oauth.mapper.ClientMapper;
import cn.com.axel.oauth.entity.OAuthClient;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * @author: axel
 * @date: 2020/2/16 15:54
 */
@Component
public class ClientTempCache extends BaseTempCache<OAuthClient> {
    @Resource
    ClientMapper clientMapper;

    @Override
    protected String buildKey(String... key) {
        return RedisPrefix.buildClientKey(key[0]);
    }

    @Override
    protected OAuthClient getFromDB(String... key) {
        return clientMapper.getClientById(key[0]);
    }
}

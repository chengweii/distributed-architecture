package com.disarch.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;

@Component
public class LockJedisAccessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LockJedisAccessor.class);

    private static final String OK_CODE = "OK";

    private static final String REMOVE_BY_VALUE_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    @Resource
    private ShardedJedisPool shardedJedisPool;

    public boolean setNotExistsWithExpireCluster(String key, String value, int expire) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            Collection<Jedis> jedisList = jedis.getAllShards();
            int totalCount = jedisList.size();
            int successCount = 0;
            for (Jedis client : jedisList) {
                try {
                    String result = client.set(key, value, "NX", "PX", expire * 1000);
                    if (OK_CODE.equals(result)) {
                        successCount++;
                    }
                } catch (Exception e) {
                    LOGGER.error("setNotExistsWithExpire failed,db:{},msg:{}", client.getDB(), e.getMessage());
                }
                if (successCount >= totalCount / 2 + 1) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            LOGGER.error("setNotExistsWithExpire failed:", e);
        }
        return false;
    }

    public boolean removeByValueCluster(String key, String value) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            Collection<Jedis> jedisList = jedis.getAllShards();
            for (Jedis client : jedisList) {
                try {
                    client.eval(REMOVE_BY_VALUE_SCRIPT, Collections.singletonList(key), Collections.singletonList(value));
                } catch (Exception e) {
                    LOGGER.error("removeByValueCluster failed,db:{},msg:{}", client.getDB(), e.getMessage());
                }
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("removeByValueCluster failed:", e);
        }
        return false;
    }

    public Long getExpiredTime(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            Collection<Jedis> jedisList = jedis.getAllShards();
            for (Jedis client : jedisList) {
                try {
                    Long result = client.ttl(key);
                    if (result != null) {
                        return result;
                    }
                } catch (Exception e) {
                    LOGGER.error("removeByValueCluster failed,db:{},msg:{}", client.getDB(), e.getMessage());
                }
            }
        } catch (Exception e) {
            LOGGER.error("getExpiredTime failed:" + key, e);
        }
        return -1L;
    }

}

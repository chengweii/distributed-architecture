package com.disarch.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;

@Component
public class UUIDJedisAccessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(UUIDJedisAccessor.class);

    @Resource
    private ShardedJedisPool shardedJedisPool;

    public Long getUUID(String key,Integer expire) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            String value = jedis.get(key);
            return value == null ? -1L : Long.valueOf(value);
        } catch (Exception e) {
            LOGGER.error("getUUID failed:", e);
        }
        return -1L;
    }
}

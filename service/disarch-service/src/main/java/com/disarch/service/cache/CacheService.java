package com.disarch.service.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class CacheService implements ICacheService{

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheService.class);

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    public byte[] get(byte[] key) {
        return new byte[0];
    }

    public String get(String key) {
        return null;
    }

    public boolean set(byte[] key, byte[] value) {
        try {
            ShardedJedis resource = shardedJedisPool.getResource();
            resource.set(key,value);
            resource.close();
        } catch (Exception e) {
            LOGGER.error("set failed:",e);
            return false;
        }
        return true;
    }

    public boolean setWithExpire(byte[] key, byte[] value, int expire) {
        try {
            ShardedJedis resource = shardedJedisPool.getResource();
            resource.setex(key,expire,value);
            resource.close();
        } catch (Exception e) {
            LOGGER.error("setWithExpire failed:",e);
            return false;
        }
        return true;
    }

    public boolean setWithExpire(String key, String value, int expire) {
        try {
            ShardedJedis resource = shardedJedisPool.getResource();
            resource.setex(key,expire,value);
            resource.close();
        } catch (Exception e) {
            LOGGER.error("setWithExpire failed:",e);
            return false;
        }
        return true;
    }

    public boolean persist(byte[] key, byte[] value) {
        return true;
    }

    public boolean expire(byte[] key, int expire) {
        return true;
    }

    public boolean remove(byte[]... key) {
        return true;
    }

    public boolean exists(byte[] key) {
        return false;
    }

    public long getTime(byte[] key) {
        return 0;
    }

    public Long incr(String key) {
        return null;
    }

    public Long decr(String key) {
        return null;
    }
}

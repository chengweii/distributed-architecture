package com.disarch.service.cache;

import com.disarch.cache.CacheJedisAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CacheService implements ICacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheService.class);

    @Resource
    private CacheJedisAccessor cacheJedisAccessor;

    @Override
    public byte[] get(byte[] key) {
        return cacheJedisAccessor.get(key);
    }

    @Override
    public String get(String key) {
        return cacheJedisAccessor.get(key);
    }

    @Override
    public boolean set(byte[] key, byte[] value) {
        return cacheJedisAccessor.set(key, value);
    }

    @Override
    public boolean setWithExpire(byte[] key, byte[] value, int expire) {
        return cacheJedisAccessor.setWithExpire(key, value, expire);
    }

    @Override
    public boolean setWithExpire(String key, String value, int expire) {
        return cacheJedisAccessor.setWithExpire(key, value, expire);
    }

    @Override
    public boolean expire(byte[] key, int expire) {
        return cacheJedisAccessor.expire(key, expire);
    }

    @Override
    public boolean remove(String key) {
        return cacheJedisAccessor.remove(key);
    }

    @Override
    public boolean remove(byte[] key) {
        return cacheJedisAccessor.remove(key);
    }

    @Override
    public boolean exists(byte[] key) {
        return cacheJedisAccessor.exists(key);
    }

    @Override
    public Long getExpiredTime(byte[] key) {
        return cacheJedisAccessor.getExpiredTime(key);
    }

    @Override
    public Long getExpiredTime(String key) {
        return cacheJedisAccessor.getExpiredTime(key);
    }

    @Override
    public Long increaseByStep(String key, int step) {
        return cacheJedisAccessor.increaseByStep(key, step);
    }

    @Override
    public Long decreaseByStep(String key, int step) {
        return cacheJedisAccessor.decreaseByStep(key, step);
    }

}

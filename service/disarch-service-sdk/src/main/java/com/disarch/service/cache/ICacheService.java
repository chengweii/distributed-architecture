package com.disarch.service.cache;

public interface ICacheService {
    byte[] get(byte[] key);

    String get(String key);

    boolean set(byte[] key, byte[] value);

    boolean setWithExpire(byte[] key, byte[] value, int expire);

    boolean setWithExpire(String key, String value, int expire);

    boolean expire(byte[] key, int expire);

    boolean remove(String key);

    boolean remove(byte[] key);

    boolean exists(byte[] key);

    Long getExpiredTime(byte[] key);

    Long getExpiredTime(String key);

    Long increaseByStep(String key, int step);

    Long decreaseByStep(String key, int step);

    boolean setNotExistsWithExpire(String key, String value, int expire);

    boolean setNotExistsWithExpireCluster(String key, String value, int expire);

    boolean removeByValueCluster(String key, String value);

    boolean removeByValue(String key, String value);
}

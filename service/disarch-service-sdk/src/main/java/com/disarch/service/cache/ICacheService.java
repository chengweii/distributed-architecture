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

    long getTime(byte[] key);

    Long incr(String key);

    Long decr(String key);
}

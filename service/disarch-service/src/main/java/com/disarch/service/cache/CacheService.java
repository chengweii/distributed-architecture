package com.disarch.service.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class CacheService implements ICacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheService.class);

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    public byte[] get(byte[] key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            byte[] datas = jedis.get(key);
            return datas == null ? new byte[0] : datas;
        } catch (Exception e) {
            LOGGER.error("get failed:", e);
        }
        return new byte[0];
    }

    public String get(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            String datas = jedis.get(key);
            return datas;
        } catch (Exception e) {
            LOGGER.error("get failed:", e);
        }
        return null;
    }

    public boolean set(byte[] key, byte[] value) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            jedis.set(key, value);
        } catch (Exception e) {
            LOGGER.error("set failed:", e);
            return false;
        }
        return true;
    }

    public boolean setWithExpire(byte[] key, byte[] value, int expire) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            jedis.setex(key, expire, value);
        } catch (Exception e) {
            LOGGER.error("setWithExpire failed:", e);
            return false;
        }
        return true;
    }

    public boolean setWithExpire(String key, String value, int expire) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            jedis.setex(key, expire, value);
        } catch (Exception e) {
            LOGGER.error("setWithExpire failed:", e);
            return false;
        }
        return true;
    }

    public boolean expire(byte[] key, int expire) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            jedis.expire(key, expire);
            return true;
        } catch (Exception e) {
            LOGGER.error("expire failed:" + key, e);
        }
        return false;
    }

    public boolean remove(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            jedis.del(key);
            return true;
        } catch (Exception e) {
            LOGGER.error("remove failed:" + key, e);
        }
        return false;
    }

    public boolean remove(byte[] key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            jedis.del(key);
            return true;
        } catch (Exception e) {
            LOGGER.error("remove failed:" + key, e);
        }
        return false;
    }

    public boolean exists(byte[] key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            Boolean result = jedis.exists(key);
            return result;
        } catch (Exception e) {
            LOGGER.error("exists failed:" + key, e);
        }
        return false;
    }

    public long getTime(byte[] key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            Long result = jedis.ttl(key);
            return result;
        } catch (Exception e) {
            LOGGER.error("getTime failed:" + key, e);
        }
        return -1L;
    }

    public Long incr(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            Long result = jedis.incr(key);
            return result;
        } catch (Exception e) {
            LOGGER.error("incr failed:", e);
        }
        return 0L;
    }

    public Long decr(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            Long result = jedis.decr(key);
            return result;
        } catch (Exception e) {
            LOGGER.error("decr failed:", e);
        }
        return 0L;
    }
}

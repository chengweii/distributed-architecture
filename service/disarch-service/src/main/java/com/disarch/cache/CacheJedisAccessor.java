package com.disarch.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;

@Component
public class CacheJedisAccessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheJedisAccessor.class);

    private static final String OK_CODE = "OK";

    @Resource
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
            String result = jedis.set(key, value);
            return OK_CODE.equals(result);
        } catch (Exception e) {
            LOGGER.error("set failed:", e);
        }
        return false;
    }

    public boolean setWithExpire(byte[] key, byte[] value, int expire) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            String result = jedis.setex(key, expire, value);
            return OK_CODE.equals(result);
        } catch (Exception e) {
            LOGGER.error("setWithExpire failed:", e);
        }
        return false;
    }

    public boolean setWithExpire(String key, String value, int expire) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            String result = jedis.setex(key, expire, value);
            return OK_CODE.equals(result);
        } catch (Exception e) {
            LOGGER.error("setWithExpire failed:", e);
        }
        return false;
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
            return jedis.del(key) > 0;
        } catch (Exception e) {
            LOGGER.error("remove failed:" + key, e);
        }
        return false;
    }

    public boolean remove(byte[] key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.del(key) > 0;
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

    public Long getExpiredTime(byte[] key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            Long result = jedis.ttl(key);
            return result;
        } catch (Exception e) {
            LOGGER.error("getExpiredTime failed:" + key, e);
        }
        return -1L;
    }

    public Long getExpiredTime(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            Long result = jedis.ttl(key);
            return result;
        } catch (Exception e) {
            LOGGER.error("getExpiredTime failed:" + key, e);
        }
        return -1L;
    }

    public Long increaseByStep(String key, int step) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            Long result = jedis.incrBy(key, step);
            return result;
        } catch (Exception e) {
            LOGGER.error("increaseByStep failed:", e);
        }
        return 0L;
    }

    public Long decreaseByStep(String key, int step) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            Long result = jedis.decrBy(key, step);
            return result;
        } catch (Exception e) {
            LOGGER.error("decreaseByStep failed:", e);
        }
        return 0L;
    }

}

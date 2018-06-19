package com.disarch.service.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;

@Service
public class CacheService implements ICacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheService.class);

    private static final String OK_CODE = "OK";

    private static final String REMOVE_BY_VALUE_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    @Resource
    private ShardedJedisPool shardedJedisPool;

    @Override
    public byte[] get(byte[] key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            byte[] datas = jedis.get(key);
            return datas == null ? new byte[0] : datas;
        } catch (Exception e) {
            LOGGER.error("get failed:", e);
        }
        return new byte[0];
    }

    @Override
    public String get(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            String datas = jedis.get(key);
            return datas;
        } catch (Exception e) {
            LOGGER.error("get failed:", e);
        }
        return null;
    }

    @Override
    public boolean set(byte[] key, byte[] value) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            String result = jedis.set(key, value);
            return OK_CODE.equals(result);
        } catch (Exception e) {
            LOGGER.error("set failed:", e);
        }
        return false;
    }

    @Override
    public boolean setWithExpire(byte[] key, byte[] value, int expire) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            String result = jedis.setex(key, expire, value);
            return OK_CODE.equals(result);
        } catch (Exception e) {
            LOGGER.error("setWithExpire failed:", e);
        }
        return false;
    }

    @Override
    public boolean setWithExpire(String key, String value, int expire) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            String result = jedis.setex(key, expire, value);
            return OK_CODE.equals(result);
        } catch (Exception e) {
            LOGGER.error("setWithExpire failed:", e);
        }
        return false;
    }

    @Override
    public boolean expire(byte[] key, int expire) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            jedis.expire(key, expire);
            return true;
        } catch (Exception e) {
            LOGGER.error("expire failed:" + key, e);
        }
        return false;
    }

    @Override
    public boolean remove(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.del(key) > 0;
        } catch (Exception e) {
            LOGGER.error("remove failed:" + key, e);
        }
        return false;
    }

    @Override
    public boolean remove(byte[] key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.del(key) > 0;
        } catch (Exception e) {
            LOGGER.error("remove failed:" + key, e);
        }
        return false;
    }

    @Override
    public boolean exists(byte[] key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            Boolean result = jedis.exists(key);
            return result;
        } catch (Exception e) {
            LOGGER.error("exists failed:" + key, e);
        }
        return false;
    }

    @Override
    public Long getExpiredTime(byte[] key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            Long result = jedis.ttl(key);
            return result;
        } catch (Exception e) {
            LOGGER.error("getExpiredTime failed:" + key, e);
        }
        return -1L;
    }

    @Override
    public Long getExpiredTime(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            Long result = jedis.ttl(key);
            return result;
        } catch (Exception e) {
            LOGGER.error("getExpiredTime failed:" + key, e);
        }
        return -1L;
    }

    @Override
    public Long increaseByStep(String key, int step) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            Long result = jedis.incrBy(key, step);
            return result;
        } catch (Exception e) {
            LOGGER.error("increaseByStep failed:", e);
        }
        return 0L;
    }

    @Override
    public Long decreaseByStep(String key, int step) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            Long result = jedis.decrBy(key, step);
            return result;
        } catch (Exception e) {
            LOGGER.error("decreaseByStep failed:", e);
        }
        return 0L;
    }

    @Override
    public boolean setNotExistsWithExpire(String key, String value, int expire) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            String result = jedis.set(key, value, "NX", "PX", expire * 1000);
            return OK_CODE.equals(result);
        } catch (Exception e) {
            LOGGER.error("setNotExistsWithExpire failed:", e);
        }
        return false;
    }

    @Override
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

    @Override
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

    @Override
    public boolean removeByValue(String key, String value) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            Object result = jedis.getShard(key).eval(REMOVE_BY_VALUE_SCRIPT, Collections.singletonList(key), Collections.singletonList(value));
            return OK_CODE.equals(result);
        } catch (Exception e) {
            LOGGER.error("removeByValue failed:", e);
        }
        return false;
    }

}

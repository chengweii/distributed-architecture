package com.disarch.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;

@Component
public class SessionJedisAccessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionJedisAccessor.class);

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

    public boolean setWithExpire(byte[] key, byte[] value, int expire) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            String result = jedis.setex(key, expire, value);
            return OK_CODE.equals(result);
        } catch (Exception e) {
            LOGGER.error("setWithExpire failed:", e);
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

}

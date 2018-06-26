package com.disarch.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class DUIDJedisAccessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DUIDJedisAccessor.class);

    private static final int DUID_INCREASE_STEP = 5;

    private static final String GET_DUID_SCRIPT = "if redis.call('get', KEYS[1]) == nil then redis.call('set', KEYS[1], ARGV[1],'EX',ARGV[2]); end return redis.call('incrby', KEYS[1],ARGV[3]);";

    private static final String OK_CODE = "OK";

    @Resource
    private ShardedJedisPool shardedJedisPool;

    public Long getDUID(String key, Integer expire) {
        Long expireTime = expire * 1000L;
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            Jedis client = jedis.getShard(key);
            List<String> argvs = new ArrayList<String>();
            argvs.add(client.getDB().toString());
            argvs.add(expireTime.toString());
            argvs.add(String.valueOf(DUID_INCREASE_STEP));
            Object result = client.eval(GET_DUID_SCRIPT, Collections.singletonList(key), argvs);
            if (result != null && OK_CODE.equals(result)) {
                return Long.valueOf(result.toString());
            }
        } catch (Exception e) {
            LOGGER.error("getDUID failed:", e);
        }
        return -1L;
    }
}

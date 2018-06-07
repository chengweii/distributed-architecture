package com.disarch.cache;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

public class ShardedJedisPoolFactoryBean implements FactoryBean<ShardedJedisPool>, InitializingBean, Closeable {
    private String address;
    private int timeout;
    private int poolMinSize;
    private int poolMaxSize;
    private long maxWaitMillis;
    private ShardedJedisPool object;
    private static final Logger LOGGER = LoggerFactory.getLogger(ShardedJedisPoolFactoryBean.class);

    public ShardedJedisPoolFactoryBean(String address, int timeout, int poolMinSize, int poolMaxSize) {
        this.address = (String) Preconditions.checkNotNull(address);
        this.timeout = timeout;
        this.poolMinSize = poolMinSize;
        this.poolMaxSize = poolMaxSize;
    }

    public void setMaxWaitMillis(long maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public void afterPropertiesSet() throws Exception {
        LOGGER.info("Initial pool for jedis: [Address: {}, timeout: {}, poolMinSize: {}, poolMaxSize: {}, maxWaitMillis: {}]", new Object[]{this.address, this.timeout, this.poolMinSize, this.poolMaxSize, this.maxWaitMillis});
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMinIdle(this.poolMinSize);
        poolConfig.setMaxTotal(this.poolMaxSize);
        poolConfig.setMaxWaitMillis(this.maxWaitMillis);
        List<JedisShardInfo> shards = Lists.newArrayList();
        String[] hostPorts = StringUtils.delimitedListToStringArray(this.address, ",");
        String[] arr$ = hostPorts;
        int len$ = hostPorts.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String hostPort = arr$[i$];
            String[] hostAndPort = StringUtils.delimitedListToStringArray(hostPort, ":");
            Preconditions.checkArgument(hostAndPort.length == 2, "Error address format: {}", new Object[]{this.address});
            shards.add(new JedisShardInfo(hostAndPort[0], Integer.parseInt(hostAndPort[1]), this.timeout));
        }

        this.object = new ShardedJedisPool(poolConfig, shards);
    }

    public ShardedJedisPool getObject() throws Exception {
        return this.object;
    }

    public Class<?> getObjectType() {
        return ShardedJedisPool.class;
    }

    public boolean isSingleton() {
        return true;
    }

    public void close() throws IOException {
        if (this.object != null) {
            this.object.destroy();
        }
    }
}


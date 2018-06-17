package com.disarch.service.lock;

import com.disarch.service.cache.ICacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

public class LockService implements ILockService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LockService.class);

    @Resource
    private ICacheService cacheService;

    private static final int DEFAULT_LOCK_EXPIRE_TIME = 10;

    private static final int DEFAULT_LOCK_WAIT_MAX_TIME = 3;

    private static final String LOCK_KEY_PREFIX = "REDIS_LOCK_";

    @Override
    public boolean lock(String lockKey, String lockId) {
        return lockWithExpire(lockKey, lockId, DEFAULT_LOCK_EXPIRE_TIME, DEFAULT_LOCK_WAIT_MAX_TIME);
    }

    @Override
    public boolean lockWithExpire(String lockKey, String lockId, int expire, int waitMaxTime) {
        boolean result = false;
        long startTimeMillis = System.currentTimeMillis();
        long waitMaxTimeMillis = waitMaxTime * 1000;
        while (!result && System.currentTimeMillis() - startTimeMillis < waitMaxTimeMillis) {
            result = cacheService.setNotExistsWithExpireCluster(getFinalLockKey(lockKey), lockId, expire);
            try {
                Thread.sleep((long) (waitMaxTimeMillis * Math.random()));
            } catch (InterruptedException e) {
                LOGGER.error("lock wait error", e);
            }
        }
        return result;
    }

    @Override
    public boolean unlock(String lockKey, String lockId) {
        return cacheService.removeByValueCluster(getFinalLockKey(lockKey), lockId);
    }

    @Override
    public long getLockInvalidTime(String lockKey) {
        return cacheService.getExpiredTime(getFinalLockKey(lockKey));
    }

    private String getFinalLockKey(String lockKey) {
        return LOCK_KEY_PREFIX + lockKey;
    }
}

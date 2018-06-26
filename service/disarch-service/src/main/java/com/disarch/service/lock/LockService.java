package com.disarch.service.lock;

import com.disarch.cache.LockJedisAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LockService implements ILockService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LockService.class);

    @Resource
    private LockJedisAccessor lockJedisAccessor;

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
            result = lockJedisAccessor.setNotExistsWithExpireCluster(getFinalLockKey(lockKey), lockId, expire);
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
        return lockJedisAccessor.removeByValueCluster(getFinalLockKey(lockKey), lockId);
    }

    @Override
    public long getLockInvalidTime(String lockKey) {
        return lockJedisAccessor.getExpiredTime(getFinalLockKey(lockKey));
    }

    private String getFinalLockKey(String lockKey) {
        return LOCK_KEY_PREFIX + lockKey;
    }
}

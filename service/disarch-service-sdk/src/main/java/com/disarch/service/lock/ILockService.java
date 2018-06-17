package com.disarch.service.lock;

/**
 * 分布式锁服务
 * 为了保证非锁定者解锁，上锁时会设定锁的拥有者标识，该标识在分布式环境下唯一（可通过UUID.randomUUID()生成），解锁时只有此标识的拥有者才能完成解锁操作。
 */
public interface ILockService {
    /**
     * 通过lockKey和lockId获取分布式锁
     *
     * @param lockKey 锁的名称
     * @param lockId  锁的拥有者标识，
     *                可通过UUID.randomUUID()生成
     * @return
     */
    boolean lock(String lockKey, String lockId);

    /**
     * 通过lockKey和lockId获取分布式锁
     *
     * @param lockKey     锁的名称
     * @param lockId      锁的拥有者标识，
     *                    可通过UUID.randomUUID()生成
     * @param expire      锁的过期时间
     * @param waitMaxTime 锁重试等待最大时间
     * @return
     */
    boolean lockWithExpire(String lockKey, String lockId, int expire, int waitMaxTime);

    /**
     * 通过lockKey和lockId解除分布式锁
     *
     * @param lockKey 锁的名称
     * @param lockId  锁的拥有者标识
     * @return
     */
    boolean unlock(String lockKey, String lockId);

    /**
     * 通过lockKey获取分布式锁的失效时间
     *
     * @param lockKey 锁的名称
     * @return
     */
    long getLockInvalidTime(String lockKey);
}

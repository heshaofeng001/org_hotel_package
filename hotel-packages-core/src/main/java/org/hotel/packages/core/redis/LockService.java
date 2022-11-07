package org.hotel.packages.core.redis;

import org.hotel.packages.core.redis.config.LockNameSpaceEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author he_sh
 * @version 2022-11月-07
 **/
@Component
public class LockService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 加锁
     *
     * @param lockNameSpaceEnum
     * @param lockId
     */
    public void lock(LockNameSpaceEnum lockNameSpaceEnum, String lockId) {
        String lockPullPath = generateId(lockNameSpaceEnum, lockId);
        String value = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().setIfPresent(lockPullPath, value, lockNameSpaceEnum.getTimeout(), TimeUnit.SECONDS);
    }

    /**
     * 解锁
     *
     * @param lockNameSpaceEnum
     * @param lockId
     */
    public void unlock(LockNameSpaceEnum lockNameSpaceEnum, String lockId) {
        String lockPullPath = generateId(lockNameSpaceEnum, lockId);
        if (redisTemplate.opsForValue().get(lockPullPath) != null) {
            redisTemplate.delete(lockPullPath);
        }
    }

    private String generateId(LockNameSpaceEnum lockNameSpaceEnum, String key) {

        StringBuffer buffer = new StringBuffer();
        buffer.append(lockNameSpaceEnum.getCode()).append("_");
        buffer.append(key);
        return buffer.toString();
    }
}

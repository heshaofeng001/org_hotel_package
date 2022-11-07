package org.hotel.packages.core.cache;

import org.hotel.packages.facade.model.base.ToString;
import org.hotel.packages.model.enums.CacheObjectConfigEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author he_sh
 * @version 2022-11月-07
 **/
@Component
public class CacheService<T extends ToString> {

    @Autowired
    private RedisTemplate<String, T> redisTemplate;

    public void put(CacheObjectConfigEnum objectConfigEnum, String key, T object) {

        String id = generateId(objectConfigEnum, key);
        redisTemplate.opsForValue().set(id, object, objectConfigEnum.getTimeout(), TimeUnit.SECONDS);
    }

    public T get(CacheObjectConfigEnum objectConfigEnum, String key) {
        String id = generateId(objectConfigEnum, key);
        return redisTemplate.opsForValue().get(id);
    }

    private String generateId(CacheObjectConfigEnum objectConfigEnum, String key) {

        StringBuffer buffer = new StringBuffer();
        buffer.append(objectConfigEnum.getCode()).append("_");
        buffer.append(key);
        return buffer.toString();
    }
}

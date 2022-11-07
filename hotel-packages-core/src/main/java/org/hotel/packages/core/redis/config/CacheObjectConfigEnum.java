package org.hotel.packages.core.redis.config;

import lombok.Getter;

/**
 * @author he_sh
 * @version 2022-11月-07
 **/
@Getter
public enum CacheObjectConfigEnum {

    CUSTOMER("CUSTOMER", 1000L),
    ;

    private String code;

    private long timeout;

    CacheObjectConfigEnum(String code, long timeout) {
        this.code = code;
        this.timeout = timeout;
    }

}

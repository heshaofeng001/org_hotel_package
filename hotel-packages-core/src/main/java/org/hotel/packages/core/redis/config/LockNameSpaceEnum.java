package org.hotel.packages.core.redis.config;

import lombok.Getter;

/**
 * @author he_sh
 * @version 2022-11月-07
 **/
@Getter
public enum LockNameSpaceEnum {

    WORK_ORDER("WORK_ORDER", 5),
    ;

    private String code;

    /**
     * 过期时间，秒为单位
     */
    private long timeout;

    LockNameSpaceEnum(String code, long timeout) {

        this.code = code;
        this.timeout = timeout;
    }
}

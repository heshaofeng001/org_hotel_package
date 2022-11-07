package org.hotel.packages.model.enums;

import com.alibaba.druid.util.StringUtils;
import lombok.Getter;

@Getter
public enum RoleTypeEnum {

    CUSTOMER("CUSTOMER"),

    ;

    private String code;

    RoleTypeEnum(String code) {
        this.code = code;
    }

    public static RoleTypeEnum getByCode(String code) {
        for (RoleTypeEnum role : RoleTypeEnum.values()) {
            if (StringUtils.equals(code, role.getCode())) {
                return role;
            }
        }
        return null;
    }

}

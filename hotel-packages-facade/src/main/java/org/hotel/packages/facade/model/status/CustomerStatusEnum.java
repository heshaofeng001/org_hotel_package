package org.hotel.packages.facade.model.status;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum CustomerStatusEnum {

    EFFECTIVE("EFFECTIVE"),

    CANCELED("CANCELED"),
    ;

    private String code;

    CustomerStatusEnum(String code) {
        this.code = code;
    }

    public static CustomerStatusEnum getByCode(String code) {

        for (CustomerStatusEnum customerStatusEnum : CustomerStatusEnum.values()) {
            if (StringUtils.equals(customerStatusEnum.getCode(), code)) {
                return customerStatusEnum;
            }
        }
        return null;
    }

}

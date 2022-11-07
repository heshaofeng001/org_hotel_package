package org.hotel.packages.facade.model.status;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum WorkOrderStatusEnum {

    INIT("INIT"),

    SUCCESS("SUCCESS"),

    ;
    private String code;

    WorkOrderStatusEnum(String code) {
        this.code = code;
    }


    public static WorkOrderStatusEnum getByCode(String code) {

        for (WorkOrderStatusEnum statusEnum : WorkOrderStatusEnum.values()) {
            if (StringUtils.equals(statusEnum.getCode(), code)) {

                return statusEnum;
            }
        }
        return null;
    }
}

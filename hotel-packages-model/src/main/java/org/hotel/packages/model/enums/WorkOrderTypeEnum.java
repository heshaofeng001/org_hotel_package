package org.hotel.packages.model.enums;

import com.alibaba.druid.util.StringUtils;
import lombok.Getter;

@Getter
public enum WorkOrderTypeEnum {

    PACKAGE_CHECK_IN("PACKAGE_CHECK_IN"),

    ;

    private String code;

    WorkOrderTypeEnum(String code) {
        this.code = code;
    }

    public static WorkOrderTypeEnum getByCode(String code) {
        for (WorkOrderTypeEnum workOrderTypeEnum : WorkOrderTypeEnum.values()) {

            if (StringUtils.equals(workOrderTypeEnum.getCode(), code)) {
                if (StringUtils.equals(code, workOrderTypeEnum.getCode())) {
                    return workOrderTypeEnum;
                }
            }
        }
        return null;
    }


}

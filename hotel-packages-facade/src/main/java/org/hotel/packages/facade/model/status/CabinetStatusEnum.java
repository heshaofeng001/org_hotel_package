package org.hotel.packages.facade.model.status;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum CabinetStatusEnum {

    FREEDOM("FREEDOM"),

    USING("USING"),
    ;

    private String code;

    CabinetStatusEnum(String code) {
        this.code = code;
    }

    public static CabinetStatusEnum getByCode(String code) {
        for (CabinetStatusEnum statusEnum : CabinetStatusEnum.values()) {
            if (StringUtils.equals(statusEnum.getCode(), code)) {
                return statusEnum;
            }
        }
        return null;
    }
}

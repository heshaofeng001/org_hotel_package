package org.hotel.packages.facade.model.status;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum CheckInStatusEnum {

    CHECK_IN("CHECK_IN"),

    CHECK_OUT("CHECK_OUT"),

    ;

    private String code;

    CheckInStatusEnum(String code) {

        this.code = code;
    }

    public static CheckInStatusEnum getByCode(String code) {

        for (CheckInStatusEnum checkInStatusEnum : CheckInStatusEnum.values()) {
            if (StringUtils.equals(checkInStatusEnum.getCode(), code)) {
                return checkInStatusEnum;
            }
        }
        return null;
    }


}

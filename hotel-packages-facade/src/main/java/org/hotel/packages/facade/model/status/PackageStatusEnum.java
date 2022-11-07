package org.hotel.packages.facade.model.status;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum PackageStatusEnum {

    CHECK_IN("CHECK_IN");;

    private String code;

    PackageStatusEnum(String code) {
        this.code = code;
    }

    public static PackageStatusEnum getByCode(String code) {

        for (PackageStatusEnum packageStatusEnum : PackageStatusEnum.values()) {
            if (StringUtils.equals(packageStatusEnum.getCode(), code)) {
                return packageStatusEnum;
            }
        }
        return null;
    }

}

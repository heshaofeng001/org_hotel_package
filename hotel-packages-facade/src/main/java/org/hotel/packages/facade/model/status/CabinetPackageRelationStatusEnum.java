package org.hotel.packages.facade.model.status;

import lombok.Getter;

@Getter
public enum CabinetPackageRelationStatusEnum {

    EFFECTIVE("EFFECTIVE"),

    ;

    private String code;

    CabinetPackageRelationStatusEnum(String code) {
        this.code = code;
    }
}

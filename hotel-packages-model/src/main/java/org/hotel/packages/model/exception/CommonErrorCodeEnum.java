package org.hotel.packages.model.exception;

import lombok.Getter;

@Getter
public enum CommonErrorCodeEnum implements ErrorCode{

    SYSTEM_ERROR("SYSTEM_ERROR"),

    CUSTOMER_STATUS_ERROR("CUSTOMER_STATUS_ERROR"),

    CUSTOMER_CHECK_OUT_ERROR("CUSTOMER_CHECK_OUT_ERROR"),

    NO_CABINET_AVAILABLE("NO_CABINET_AVAILABLE"),

    ;

    private String code;

    CommonErrorCodeEnum(String code){
        this.code = code;
    }

    @Override
    public String getErrorCode() {
        return code;
    }
}

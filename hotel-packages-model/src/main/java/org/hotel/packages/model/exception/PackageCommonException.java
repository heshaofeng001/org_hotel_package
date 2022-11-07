package org.hotel.packages.model.exception;

import lombok.Data;

@Data
public class PackageCommonException extends RuntimeException {

    private ErrorCode errorCode;

    private Exception e;


    public PackageCommonException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public PackageCommonException(ErrorCode errorCode, Exception e) {
        this.errorCode = errorCode;
        this.e = e;
    }
}

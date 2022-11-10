package org.hotel.packages.web.model;

import lombok.Data;

@Data
public class GatewayResult<T> {

    private boolean success;

    private String errorCode;

    private String errorMsg;

    private T data;
}

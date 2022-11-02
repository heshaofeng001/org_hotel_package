package org.hotel.packages.facade.result;

import lombok.Data;

@Data
public abstract class BaseResult {


    private boolean result;

    private String errorCode;

    private String errorMsg;
}

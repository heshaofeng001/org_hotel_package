package org.hotel.packages.facade.result;

import lombok.Data;
import org.hotel.packages.facade.model.ToString;

@Data
public class Result<T> extends ToString {

    private T data;

    private boolean result;

    private String errorCode;

    private String errorMsg;
}

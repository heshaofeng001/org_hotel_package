package org.hotel.packages.facade.result;

import lombok.Data;
import org.hotel.packages.facade.model.base.ToString;

@Data
public abstract class BaseResult extends ToString {


    private boolean success;

    private String errorCode;

    private String errorMsg;
}

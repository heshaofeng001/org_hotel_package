package org.hotel.packages.facade.request;

import lombok.Data;
import org.hotel.packages.facade.model.OperateContext;
import org.hotel.packages.facade.model.base.ToString;

@Data
public class BaseRequest extends ToString {

    private String requestId;

    private OperateContext operateContext;
}

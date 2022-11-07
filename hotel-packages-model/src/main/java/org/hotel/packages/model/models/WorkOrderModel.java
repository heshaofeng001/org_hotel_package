package org.hotel.packages.model.models;

import lombok.Data;
import org.hotel.packages.facade.model.base.ToString;
import org.hotel.packages.facade.model.status.WorkOrderStatusEnum;
import org.hotel.packages.model.enums.WorkOrderTypeEnum;

import java.util.HashMap;
import java.util.Map;

@Data
public class WorkOrderModel extends ToString {

    private String orderId;

    private String requestId;

    private WorkOrderTypeEnum orderType;

    private WorkOrderStatusEnum status;

    /**
     * 业务信息
     */
    private Map<String, String> extInfo = new HashMap<String, String>();
}

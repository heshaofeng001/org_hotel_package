package org.hotel.packages.core.model;

import lombok.Data;
import org.hotel.packages.model.enums.WorkOrderTypeEnum;

@Data
public class WorkOrderQueryCondition {

    private String requestId;

    private WorkOrderTypeEnum type;
}

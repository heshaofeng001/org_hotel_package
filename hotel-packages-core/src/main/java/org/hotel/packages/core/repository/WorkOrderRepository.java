package org.hotel.packages.core.repository;

import org.hotel.packages.core.model.WorkOrderQueryCondition;
import org.hotel.packages.model.models.WorkOrderModel;

import java.util.List;

public interface WorkOrderRepository {

    int save(WorkOrderModel workOrderModel);

    List<WorkOrderModel> queryByCondition(WorkOrderQueryCondition condition);

    int update(WorkOrderModel workOrderModel);

}

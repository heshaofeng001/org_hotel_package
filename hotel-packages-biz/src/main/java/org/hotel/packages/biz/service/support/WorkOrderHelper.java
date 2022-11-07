package org.hotel.packages.biz.service.support;

import org.hotel.packages.facade.request.BaseRequest;
import org.hotel.packages.facade.model.status.WorkOrderStatusEnum;
import org.hotel.packages.model.enums.WorkOrderTypeEnum;
import org.hotel.packages.model.models.WorkOrderModel;
import org.hotel.packages.core.model.WorkOrderQueryCondition;
import org.hotel.packages.core.repository.WorkOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class WorkOrderHelper {

    @Autowired
    private WorkOrderRepository workOrderRepository;

    public WorkOrderModel generate(WorkOrderTypeEnum workOrderType, BaseRequest request) {

        WorkOrderQueryCondition condition = new WorkOrderQueryCondition();
        condition.setRequestId(request.getRequestId());
        condition.setType(workOrderType);
        List<WorkOrderModel> workOrderModels = workOrderRepository.queryByCondition(condition);
        if (CollectionUtils.isEmpty(workOrderModels)) {
            WorkOrderModel workOrderModel = new WorkOrderModel();
            workOrderModel.setOrderType(workOrderType);
            workOrderModel.setRequestId(request.getRequestId());
            workOrderModel.setStatus(WorkOrderStatusEnum.INIT);
            workOrderRepository.save(workOrderModel);
            return workOrderModel;
        } else {
            Assert.isTrue(workOrderModels.size() == 1, "工单录入重复");
            return workOrderModels.get(0);
        }

    }
}

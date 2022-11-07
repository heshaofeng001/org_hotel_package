package org.hotel.packages.biz.process.pkcheckIn;

import org.hotel.packages.biz.model.PackageCheckInProcessContext;
import org.hotel.packages.biz.process.ProcessAction;
import org.hotel.packages.core.redis.LockService;
import org.hotel.packages.core.redis.config.LockNameSpaceEnum;
import org.hotel.packages.facade.model.packages.CabinetDTO;
import org.hotel.packages.facade.result.Result;
import org.hotel.packages.model.models.WorkOrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author he_sh
 * @version 2022-11月-07
 **/
@Component
public class PackageStoredCallBackAction implements ProcessAction<CabinetDTO, PackageCheckInProcessContext> {

    @Autowired
    private LockService lockService;

    @Override
    public Result<CabinetDTO> execute(PackageCheckInProcessContext context) {
        WorkOrderModel workOrderModel = context.getWorkOrderModel();
        try {
            return null;
        } finally {
            lockService.unlock(LockNameSpaceEnum.WORK_ORDER, workOrderModel.getOrderId());
        }

    }
}

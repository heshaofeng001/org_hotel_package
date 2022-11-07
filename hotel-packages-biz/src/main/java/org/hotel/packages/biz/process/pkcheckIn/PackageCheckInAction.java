package org.hotel.packages.biz.process.pkcheckIn;

import org.hotel.packages.biz.model.PackageCheckInProcessContext;
import org.hotel.packages.biz.process.ProcessAction;
import org.hotel.packages.biz.service.CabinetAllocateService;
import org.hotel.packages.biz.service.PackageEnrollService;
import org.hotel.packages.core.redis.LockService;
import org.hotel.packages.core.redis.config.LockNameSpaceEnum;
import org.hotel.packages.facade.model.packages.CabinetDTO;
import org.hotel.packages.facade.model.customer.CustomerDTO;
import org.hotel.packages.facade.model.packages.PackageDTO;
import org.hotel.packages.facade.result.Result;
import org.hotel.packages.model.models.WorkOrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author he_sh
 * @version 2022-11月-07
 **/
@Component
public class PackageCheckInAction implements ProcessAction<CabinetDTO, PackageCheckInProcessContext> {

    @Autowired
    private PackageEnrollService packageEnrollService;

    @Autowired
    private CabinetAllocateService cabinetAllocateService;

    @Autowired
    private LockService lockService;

    @Override
    public Result<CabinetDTO> execute(PackageCheckInProcessContext context) {

        WorkOrderModel workOrderModel = context.getWorkOrderModel();
        try {
            lockService.lock(LockNameSpaceEnum.WORK_ORDER, workOrderModel.getOrderId());

            //1、登记包裹信息(登记接口幂等)
            packageEnrollService.enrollPackages(context.getCustomer(), context.getPackages());

            //2、分配柜子，并保存包裹VS柜子的关联关系(幂等逻辑：一个工单只能有分配一个柜子，并放在order中)
            Result<CabinetDTO> allocateResult = cabinetAllocateService.allocate(workOrderModel, context.getPackages());
            return allocateResult;

        } finally {
            lockService.unlock(LockNameSpaceEnum.WORK_ORDER, workOrderModel.getOrderId());
        }
    }
}

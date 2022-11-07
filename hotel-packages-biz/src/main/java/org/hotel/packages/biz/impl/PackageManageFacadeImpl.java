package org.hotel.packages.biz.impl;

import org.apache.commons.lang3.StringUtils;
import org.hotel.packages.biz.impl.template.FacadeExecuteTemplate;
import org.hotel.packages.biz.model.PackageCheckInProcessContext;
import org.hotel.packages.biz.process.pkcheckIn.PackageCheckInAction;
import org.hotel.packages.biz.service.CustomerQueryService;
import org.hotel.packages.biz.service.PackageQueryService;
import org.hotel.packages.biz.service.support.WorkOrderHelper;
import org.hotel.packages.biz.vaildate.PackageCheckInValidate;
import org.hotel.packages.core.model.PackageQueryCondition;
import org.hotel.packages.facade.api.PackageManageFacade;
import org.hotel.packages.facade.model.OperateContext;
import org.hotel.packages.facade.model.customer.CustomerDTO;
import org.hotel.packages.facade.model.packages.CabinetDTO;
import org.hotel.packages.facade.model.packages.PackageDTO;
import org.hotel.packages.facade.model.status.PackageStatusEnum;
import org.hotel.packages.facade.request.BatchQueryPackagesRequest;
import org.hotel.packages.facade.request.CheckInConsultRequest;
import org.hotel.packages.facade.request.PackageCheckInRequest;
import org.hotel.packages.facade.result.BatchQueryResult;
import org.hotel.packages.facade.result.Result;
import org.hotel.packages.model.enums.RoleTypeEnum;
import org.hotel.packages.model.enums.WorkOrderTypeEnum;
import org.hotel.packages.model.models.WorkOrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 包裹管理结果
 *
 * @author he_sh
 * @version 2022-11月-05
 **/
@Service
public class PackageManageFacadeImpl extends FacadeExecuteTemplate implements PackageManageFacade {

    @Autowired
    private CustomerQueryService customerQueryService;

    @Autowired
    private PackageCheckInValidate packageCheckInValidate;

    @Autowired
    private PackageCheckInAction packageCheckInAction;

    @Autowired
    private WorkOrderHelper workOrderHelper;

    @Autowired
    private PackageQueryService packageQueryService;

    @Override
    public Result<String> checkInConsult(CheckInConsultRequest request) {
        Result<String> checkInConsultResult = new Result<String>();

        doTrans(request, checkInConsultResult, new CallBack() {
            @Override
            public void onCall() {
                CustomerDTO customerDTO = customerQueryService.queryByCustomerId(request.getCustomerId());
                Assert.notNull(customerDTO);

                List<PackageDTO> packages = createPackages(request.getCustomerId(), request.getPackages(), request.getOperateContext());
                packageCheckInValidate.check(customerDTO, packages);
                checkInConsultResult.setSuccess(Boolean.TRUE);
            }
        });
        return checkInConsultResult;
    }

    @Override
    public Result<CabinetDTO> checkIn(PackageCheckInRequest request) {

        Result<CabinetDTO> checkInResult = new Result<CabinetDTO>();

        doTrans(request, checkInResult, new CallBack() {
            @Override
            public void onCall() {
                CustomerDTO customerDTO = customerQueryService.queryByCustomerId(request.getCustomerId());
                Assert.notNull(customerDTO);
                List<PackageDTO> packages = createPackages(request.getCustomerId(), request.getPackages(), request.getOperateContext());
                packageCheckInValidate.check(customerDTO, packages);

                WorkOrderModel workOrderModel = workOrderHelper.generate(WorkOrderTypeEnum.PACKAGE_CHECK_IN, request);

                //保存工单，然后受理处理（分配足够的存储柜）
                PackageCheckInProcessContext packageCheckInProcessContext = PackageCheckInProcessContext.newInstance(workOrderModel, customerDTO, packages, request.getOperateContext());
                Result<CabinetDTO> allocateResult = packageCheckInAction.execute(packageCheckInProcessContext);

                checkInResult.setData(allocateResult.getData());
                checkInResult.setSuccess(allocateResult.isSuccess());
            }
        });
        return checkInResult;
    }

    @Override
    public BatchQueryResult<PackageDTO> batchQueryPackages(BatchQueryPackagesRequest request) {
        BatchQueryResult<PackageDTO> packageDTOBatchQueryResult = new BatchQueryResult<PackageDTO>();

        doTrans(request, packageDTOBatchQueryResult, new CallBack() {
            @Override
            public void onCall() {

                PackageQueryCondition condition = new PackageQueryCondition();
                condition.setOwnerId(request.getCustomerId());
                if (StringUtils.isNotBlank(request.getPackageStatus())) {
                    condition.setPackageStatus(request.getPackageStatus());
                }
                if (CollectionUtils.isEmpty(request.getWaybillIds())) {
                    condition.setWaybillIds(request.getWaybillIds());
                }

                List<PackageDTO> packageDTOS = packageQueryService.query(condition);
                packageDTOBatchQueryResult.setData(packageDTOS);
                packageDTOBatchQueryResult.setSuccess(Boolean.TRUE);
            }
        });
        return packageDTOBatchQueryResult;
    }

    /**
     * 创建包裹
     *
     * @param packages
     * @return
     */
    private List<PackageDTO> createPackages(String customerId, List<PackageDTO> packages, OperateContext operateContext) {
        List<PackageDTO> packageDTOS = new ArrayList<>();
        for (PackageDTO packageInfo : packages) {
            PackageDTO packageDTO = new PackageDTO();
            //包裹原始信息（用于判断分配多大存储空间）
            packageDTO.setWaybillId(packageInfo.getWaybillId());
            packageDTO.setReservePhone(packageInfo.getReservePhone());
            packageDTO.setPackageSize(packageInfo.getPackageSize());
            packageDTO.setPackageStatus(PackageStatusEnum.CHECK_IN.getCode());
            packageDTO.setOwnerId(customerId);
            packageDTO.setOwnerType(RoleTypeEnum.CUSTOMER.getCode());

            packageDTO.setReceiveId(operateContext.getOperateId());
            packageDTO.setReceiveType(operateContext.getOperateType());
            packageDTO.setReceiveDate(new Date());
            packageDTOS.add(packageDTO);
        }
        return packageDTOS;
    }
}

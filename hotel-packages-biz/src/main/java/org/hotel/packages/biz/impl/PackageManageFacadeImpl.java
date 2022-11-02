package org.hotel.packages.biz.impl;

import org.hotel.packages.biz.service.CustomerQueryService;
import org.hotel.packages.biz.service.component.PackageCheckInComp;
import org.hotel.packages.biz.vaildate.PackageCheckInValidate;
import org.hotel.packages.facade.api.PackageManageFacade;
import org.hotel.packages.facade.model.CabinetDTO;
import org.hotel.packages.facade.model.CustomerDTO;
import org.hotel.packages.facade.model.PackageBaseDTO;
import org.hotel.packages.facade.model.PackageDTO;
import org.hotel.packages.facade.request.PackageCheckInRequest;
import org.hotel.packages.facade.result.Result;
import org.hotel.packages.model.models.CabinetModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class PackageManageFacadeImpl implements PackageManageFacade {

    @Autowired
    private CustomerQueryService customerQueryService;

    @Autowired
    private PackageCheckInValidate packageCheckInValidate;

    @Autowired
    private PackageCheckInComp packageCheckInComp;

    @Override
    public Result<CabinetDTO> checkIn(PackageCheckInRequest request) {

        Result<CabinetDTO> checkInResult = new Result<CabinetDTO>();

        CustomerDTO customerDTO = customerQueryService.queryByCustomerId(request.getCustomerId());
        Assert.notNull(customerDTO);
        List<PackageDTO> packages = createPackages(request);
        packageCheckInValidate.check(customerDTO, packages);

        //可以先保存工单，然后受理处理（分配足够的存储柜）
        CabinetDTO cabinetDTO = packageCheckInComp.checkIn(customerDTO, packages);
        checkInResult.setData(cabinetDTO);
        return checkInResult;
    }

    private List<PackageDTO> createPackages(PackageCheckInRequest request) {
        List<PackageDTO> packageDTOS = new ArrayList<>();
        for (PackageBaseDTO packageInfo : request.getPackages()) {
            PackageDTO packageDTO = new PackageDTO();
            //包裹原始信息（用于判断分配多大存储空间）
            packageDTO.setReservePhone(packageInfo.getReservePhone());
            packageDTO.setPackageSize(packageInfo.getPackageSize());
            packageDTO.setPackageStatus("");
            packageDTO.setOwnerId(request.getCustomerId());
            packageDTOS.add(packageDTO);
        }
        return packageDTOS;
    }
}

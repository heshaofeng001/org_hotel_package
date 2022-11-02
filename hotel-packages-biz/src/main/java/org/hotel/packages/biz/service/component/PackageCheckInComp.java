package org.hotel.packages.biz.service.component;

import org.hotel.packages.biz.service.CabinetManageService;
import org.hotel.packages.facade.model.CabinetDTO;
import org.hotel.packages.facade.model.CustomerDTO;
import org.hotel.packages.facade.model.PackageDTO;

import java.util.List;

public class PackageCheckInComp {

    private CabinetManageService cabinetManageService;

    public CabinetDTO checkIn(CustomerDTO customer, List<PackageDTO> packages) {

        //1、等级包裹信息

        //2、分配规则（并保存包裹VS柜子的关联关系）
        CabinetDTO cabinetDTO = cabinetManageService.allocate(packages);

        return cabinetDTO;
    }
}

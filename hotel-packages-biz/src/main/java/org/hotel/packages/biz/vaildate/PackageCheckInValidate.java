package org.hotel.packages.biz.vaildate;

import com.alibaba.druid.util.StringUtils;
import org.hotel.packages.biz.service.CabinetManageService;
import org.hotel.packages.facade.model.CustomerDTO;
import org.hotel.packages.facade.model.PackageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PackageCheckInValidate {

    /**
     * 橱柜服务
     */
    @Autowired
    private CabinetManageService cabinetManageService;


    /**
     * 包裹寄存校验
     *
     * @param customerDTO
     * @param packages
     */
    public void check(CustomerDTO customerDTO, List<PackageDTO> packages) {

        if (StringUtils.equals(customerDTO.getStatus(), "")) {
            throw new RuntimeException();
        }

        boolean hasAvailableCabinet = cabinetManageService.allocateAble(packages);
        if (!hasAvailableCabinet) {
            throw new RuntimeException("没有可用存储柜");
        }
    }

}

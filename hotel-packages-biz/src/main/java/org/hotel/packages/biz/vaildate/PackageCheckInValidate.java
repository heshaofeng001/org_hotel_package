package org.hotel.packages.biz.vaildate;

import com.alibaba.druid.util.StringUtils;
import org.hotel.packages.biz.service.CabinetAllocateService;
import org.hotel.packages.facade.model.customer.CustomerDTO;
import org.hotel.packages.facade.model.packages.PackageDTO;
import org.hotel.packages.facade.model.status.CheckInStatusEnum;
import org.hotel.packages.facade.model.status.CustomerStatusEnum;
import org.hotel.packages.model.exception.CommonErrorCodeEnum;
import org.hotel.packages.model.exception.PackageCommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PackageCheckInValidate {

    /**
     * 橱柜服务
     */
    @Autowired
    private CabinetAllocateService cabinetAllocateService;


    /**
     * 包裹寄存校验
     *
     * @param customerDTO
     * @param packages
     */
    public void check(CustomerDTO customerDTO, List<PackageDTO> packages) {

        if (!StringUtils.equals(customerDTO.getStatus(), CustomerStatusEnum.EFFECTIVE.getCode())) {
            throw new PackageCommonException(CommonErrorCodeEnum.CUSTOMER_STATUS_ERROR);
        }
        if (!StringUtils.equals(customerDTO.getCheckInStatus(), CheckInStatusEnum.CHECK_IN.getCode())) {
            throw new PackageCommonException(CommonErrorCodeEnum.CUSTOMER_CHECK_OUT_ERROR);
        }
        boolean hasAvailableCabinet = cabinetAllocateService.allocateAble(packages);
        if (!hasAvailableCabinet) {
            throw new PackageCommonException(CommonErrorCodeEnum.NO_CABINET_AVAILABLE);
        }
    }

}

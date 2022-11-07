package org.hotel.packages.model.models;

import lombok.Data;
import org.hotel.packages.facade.model.base.ToString;
import org.hotel.packages.facade.model.status.CheckInStatusEnum;
import org.hotel.packages.facade.model.status.CustomerStatusEnum;

import java.util.Date;

@Data
public class CustomerModel extends ToString {

    /**
     * 系统唯一ID
     */
    private String customerId;

    private String customerName;

    private CustomerStatusEnum customerStatus;

    /**
     * 身份证号
     */
    private String idCardNo;

    ///////////////////人住信息（应该拆分出人住流水表）///////////////////////
    private CheckInStatusEnum checkInStatus;

    private Date checkInDate;

    private Date checkOutDate;
}

package org.hotel.packages.model.models;

import lombok.Data;
import org.hotel.packages.facade.model.base.ToString;
import org.hotel.packages.facade.model.status.PackageStatusEnum;
import org.hotel.packages.model.enums.RoleTypeEnum;

import java.util.Date;

@Data
public class PackageModel extends ToString {

    private String packageId;

    private String ownerId;

    /**
     * 归属人类型
     */
    private RoleTypeEnum ownerType;

    private PackageStatusEnum status;

    /**
     * 运单号（物流公司暂时省略）
     */
    private String waybillId;


    /**
     * 接收人ID
     */
    private String receiveId;

    private String receiveType;

    private Date receiveDate;

    private Date checkOutDate;
}

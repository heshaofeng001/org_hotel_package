package org.hotel.packages.facade.model.packages;

import lombok.Data;

import java.util.Date;

@Data
public class PackageDTO extends PackageBaseDTO {

    /**
     * 包裹系统唯一ID
     */
    private String packageId;

    /**
     * 包裹状态
     */
    private String packageStatus;

    /**
     * 包裹归属人
     */
    private String ownerId;

    private String ownerType;

    /**
     * 运单号（物流公司暂时省略）
     */
    private String waybillId;

    private String receiveId;

    private String receiveType;


    private Date checkOutDate;
}

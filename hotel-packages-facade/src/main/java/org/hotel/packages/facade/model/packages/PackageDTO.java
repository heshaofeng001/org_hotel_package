package org.hotel.packages.facade.model.packages;

import lombok.Data;

import java.util.Date;

@Data
public class PackageDTO extends PackageBaseDTO {

    /**
     * ����ϵͳΨһID
     */
    private String packageId;

    /**
     * ����״̬
     */
    private String packageStatus;

    /**
     * ����������
     */
    private String ownerId;

    private String ownerType;

    /**
     * �˵��ţ�������˾��ʱʡ�ԣ�
     */
    private String waybillId;

    private String receiveId;

    private String receiveType;


    private Date checkOutDate;
}

package org.hotel.packages.facade.model;

import lombok.Data;

import java.util.Date;

@Data
public class PackageDTO extends PackageBaseDTO{


    private String packageId;

    private String packageStatus;

    private String ownerId;

    private String ownerType;

    private String receiveId;

    private String receiveType;


    private Date checkOutDate;
}

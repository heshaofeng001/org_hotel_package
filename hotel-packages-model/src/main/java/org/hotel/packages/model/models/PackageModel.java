package org.hotel.packages.model.models;

import org.hotel.packages.facade.model.ToString;
import org.hotel.packages.model.enums.PackageStatusEnum;

public class PackageModel extends ToString {

    private String packageId;

    private String ownerId;

    private PackageStatusEnum status;
}

package org.hotel.packages.biz.convert;

import org.hotel.packages.facade.model.packages.PackageDTO;
import org.hotel.packages.facade.model.status.PackageStatusEnum;
import org.hotel.packages.model.enums.RoleTypeEnum;
import org.hotel.packages.model.models.PackageModel;

public class PackageConvertor {

    public static PackageDTO convertToDTO(PackageModel packageModel) {
        PackageDTO packageDTO = new PackageDTO();
        packageDTO.setPackageId(packageModel.getPackageId());
        packageDTO.setPackageStatus(packageModel.getStatus().getCode());
        packageDTO.setCheckOutDate(packageModel.getCheckOutDate());
        packageDTO.setReceiveId(packageModel.getReceiveId());
        packageDTO.setOwnerId(packageModel.getOwnerId());
        packageDTO.setOwnerType(packageModel.getOwnerType().getCode());
        packageDTO.setWaybillId(packageModel.getWaybillId());
        return packageDTO;
    }

    public static PackageModel convertToModel(PackageDTO packageDTO) {
        PackageModel packageModel = new PackageModel();
        packageModel.setPackageId(packageDTO.getPackageId());
        packageModel.setWaybillId(packageDTO.getWaybillId());
        packageModel.setStatus(PackageStatusEnum.getByCode(packageDTO.getPackageStatus()));
        packageModel.setOwnerId(packageDTO.getOwnerId());
        packageModel.setCheckOutDate(packageDTO.getCheckOutDate());
        packageModel.setReceiveDate(packageDTO.getReceiveDate());
        packageModel.setOwnerType(RoleTypeEnum.CUSTOMER);
        packageModel.setReceiveId(packageDTO.getReceiveId());
        packageModel.setReceiveType(packageDTO.getReceiveType());
        return packageModel;
    }
}

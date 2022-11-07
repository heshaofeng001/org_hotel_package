package org.hotel.packages.core.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.hotel.packages.core.model.PackageQueryCondition;
import org.hotel.packages.dal.PackageInfoBOMapper;
import org.hotel.packages.dal.idgenerate.IdGenerate;
import org.hotel.packages.dal.idgenerate.TableEnums;
import org.hotel.packages.dal.po.PackageInfoBO;
import org.hotel.packages.dal.po.PackageInfoBOExample;
import org.hotel.packages.facade.model.status.PackageStatusEnum;
import org.hotel.packages.model.enums.RoleTypeEnum;
import org.hotel.packages.model.models.PackageModel;
import org.hotel.packages.core.repository.PackageInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class PackageInfoRepositoryImpl implements PackageInfoRepository {

    @Autowired
    private IdGenerate idGenerate;

    @Autowired
    private PackageInfoBOMapper packageInfoBOMapper;

    @Override
    public int save(PackageModel packageModel) {
        PackageInfoBO packageInfoBO = convertToBo(packageModel);
        if (StringUtils.isBlank(packageInfoBO.getPackageId())) {
            packageInfoBO.setPackageId(idGenerate.generateId(TableEnums.HOTEL_PACKAGE));
        }
        return packageInfoBOMapper.insert(packageInfoBO);
    }

    @Override
    public List<PackageModel> queryByCondition(PackageQueryCondition condition) {

        PackageInfoBOExample packageInfoBOExample = new PackageInfoBOExample();
        PackageInfoBOExample.Criteria criteria = packageInfoBOExample.createCriteria();
        criteria.andOwnerIdEqualTo(condition.getOwnerId());
        if (condition.getOwnerType() != null) {
            criteria.andOwnerTypeEqualTo(condition.getOwnerType().getCode());
        }
        if (!CollectionUtils.isEmpty(condition.getWaybillIds())) {
            criteria.andWayBillIdIn(condition.getWaybillIds());
        }
        if (StringUtils.isNotBlank(condition.getPackageStatus())) {
            criteria.andPackageStatusEqualTo(condition.getPackageStatus());
        }
        List<PackageInfoBO> packageInfoBOS = packageInfoBOMapper.selectByExample(packageInfoBOExample);
        if (CollectionUtils.isEmpty(packageInfoBOS)) {
            return null;
        }
        List<PackageModel> models = new ArrayList<>();
        for (PackageInfoBO packageInfoBO : packageInfoBOS) {
            models.add(convertToModel(packageInfoBO));
        }
        return models;
    }

    private PackageInfoBO convertToBo(PackageModel packageModel) {
        PackageInfoBO packageInfoBO = new PackageInfoBO();
        packageInfoBO.setPackageId(packageModel.getPackageId());
        packageInfoBO.setPackageStatus(packageModel.getStatus().getCode());
        packageInfoBO.setReceiveId(packageModel.getReceiveId());
        packageInfoBO.setWayBillId(packageModel.getWaybillId());
        packageInfoBO.setOwnerId(packageModel.getOwnerId());
        packageInfoBO.setOwnerType(packageModel.getOwnerType().getCode());
        packageInfoBO.setCheckOutDate(packageModel.getCheckOutDate());
        packageInfoBO.setReceiveDate(packageModel.getReceiveDate());
        packageInfoBO.setReceiveType("");
        return packageInfoBO;
    }

    private PackageModel convertToModel(PackageInfoBO packageInfoBO) {
        PackageModel packageModel = new PackageModel();
        packageModel.setPackageId(packageInfoBO.getPackageId());
        packageModel.setStatus(PackageStatusEnum.getByCode(packageInfoBO.getPackageStatus()));
        packageModel.setWaybillId(packageInfoBO.getWayBillId());
        packageModel.setReceiveId(packageInfoBO.getReceiveId());
        packageModel.setReceiveType(packageInfoBO.getReceiveType());
        packageModel.setOwnerId(packageInfoBO.getOwnerId());
        packageModel.setOwnerType(RoleTypeEnum.getByCode(packageInfoBO.getOwnerType()));
        packageModel.setReceiveDate(packageInfoBO.getReceiveDate());
        return packageModel;
    }
}

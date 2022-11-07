package org.hotel.packages.core.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.hotel.packages.core.model.PackageRelationQueryCondition;
import org.hotel.packages.core.repository.CabinetPackageRelationRepository;
import org.hotel.packages.dal.CabinetPackageRelationBOMapper;
import org.hotel.packages.dal.po.CabinetPackageRelationBO;
import org.hotel.packages.dal.po.CabinetPackageRelationBOExample;
import org.hotel.packages.model.models.CabinetPackageRelationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class CabinetPackageRelationRepositoryImpl implements CabinetPackageRelationRepository {

    @Autowired
    private CabinetPackageRelationBOMapper cabinetPackageRelationBOMapper;

    @Override
    public void batchSave(List<CabinetPackageRelationModel> relationModels) {

        for (CabinetPackageRelationModel cabinetPackageRelationModel : relationModels) {
            cabinetPackageRelationBOMapper.insert(convertToBO(cabinetPackageRelationModel));
        }
    }

    @Override
    public List<CabinetPackageRelationModel> queryByCondition(PackageRelationQueryCondition condition) {
        CabinetPackageRelationBOExample cabinetPackageRelationBOExample = new CabinetPackageRelationBOExample();
        CabinetPackageRelationBOExample.Criteria criteria = cabinetPackageRelationBOExample.createCriteria();
        criteria.andPackageIdIn(condition.getPackageIds());
        if (!StringUtils.isBlank(condition.getRelationStatus())) {
            criteria.andStatusEqualTo(condition.getRelationStatus());
        }
        List<CabinetPackageRelationBO> relationBOS = cabinetPackageRelationBOMapper.selectByExample(cabinetPackageRelationBOExample);
        return convertToModel(relationBOS);
    }

    private List<CabinetPackageRelationModel> convertToModel(List<CabinetPackageRelationBO> cabinetPackageRelationBOs) {
        if (CollectionUtils.isEmpty(cabinetPackageRelationBOs)) {
            return null;
        }
        List<CabinetPackageRelationModel> models = new ArrayList<>();
        for (CabinetPackageRelationBO cabinetPackageRelationBO : cabinetPackageRelationBOs) {
            models.add(convertToModel(cabinetPackageRelationBO));
        }
        return models;
    }

    private CabinetPackageRelationModel convertToModel(CabinetPackageRelationBO cabinetPackageRelationBO) {
        CabinetPackageRelationModel cabinetPackageRelationModel = new CabinetPackageRelationModel();
        cabinetPackageRelationModel.setPackageId(cabinetPackageRelationBO.getPackageId());
        cabinetPackageRelationModel.setCabinetId(cabinetPackageRelationBO.getCabinetId());
        cabinetPackageRelationModel.setStatus(cabinetPackageRelationBO.getStatus());
        return cabinetPackageRelationModel;
    }

    private CabinetPackageRelationBO convertToBO(CabinetPackageRelationModel cabinetPackageRelationModel) {
        CabinetPackageRelationBO cabinetPackageRelationBO = new CabinetPackageRelationBO();
        cabinetPackageRelationBO.setCabinetId(cabinetPackageRelationModel.getCabinetId());
        cabinetPackageRelationBO.setPackageId(cabinetPackageRelationModel.getPackageId());
        cabinetPackageRelationBO.setStatus(cabinetPackageRelationModel.getStatus());
        return cabinetPackageRelationBO;
    }
}

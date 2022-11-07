package org.hotel.packages.core.repository.impl;

import org.hotel.packages.core.repository.CabinetPackageRelationRepository;
import org.hotel.packages.dal.CabinetPackageRelationBOMapper;
import org.hotel.packages.dal.po.CabinetPackageRelationBO;
import org.hotel.packages.model.models.CabinetPackageRelationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    private CabinetPackageRelationBO convertToBO(CabinetPackageRelationModel cabinetPackageRelationModel) {
        CabinetPackageRelationBO cabinetPackageRelationBO = new CabinetPackageRelationBO();
        cabinetPackageRelationBO.setCabinetId(cabinetPackageRelationModel.getCabinetId());
        cabinetPackageRelationBO.setPackageId(cabinetPackageRelationModel.getPackageId());
        cabinetPackageRelationBO.setStatus(cabinetPackageRelationModel.getStatus());
        return cabinetPackageRelationBO;
    }
}

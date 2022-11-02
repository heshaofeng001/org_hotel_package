package org.packages.core.repository.impl;

import org.hotel.packages.dal.CabinetPackageRelationBOMapper;
import org.hotel.packages.model.models.CabinetPackageRelationModel;
import org.packages.core.repository.CabinetPackageRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CabinetPackageRelationRepositoryImpl implements CabinetPackageRelationRepository {

    @Autowired
    private CabinetPackageRelationBOMapper cabinetPackageRelationBOMapper;

    @Override
    public int batchSave(List<CabinetPackageRelationModel> relationModels) {

        for (CabinetPackageRelationModel cabinetPackageRelationModel : relationModels) {
            cabinetPackageRelationBOMapper.insert(null);
        }

        return 0;
    }
}

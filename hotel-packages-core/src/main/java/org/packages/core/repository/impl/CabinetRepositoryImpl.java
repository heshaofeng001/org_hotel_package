package org.packages.core.repository.impl;

import org.hotel.packages.model.models.CabinetModel;
import org.packages.core.model.CabinetQueryCondition;
import org.packages.core.repository.CabinetRepository;

import java.util.List;

public class CabinetRepositoryImpl implements CabinetRepository {

    @Override
    public int save(CabinetModel cabinetModel) {
        return 0;
    }

    @Override
    public List<CabinetModel> queryByCondition(CabinetQueryCondition condition) {
        return null;
    }
}

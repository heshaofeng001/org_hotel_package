package org.hotel.packages.core.repository;

import org.hotel.packages.model.models.CabinetModel;
import org.hotel.packages.core.model.CabinetQueryCondition;

import java.util.List;

public interface CabinetRepository {

    int save(CabinetModel cabinetModel);

    List<CabinetModel> queryByCondition(CabinetQueryCondition condition);

    CabinetModel queryByCabinetId(String cabinetId);

    CabinetModel lock(String cabinetId);

    int update(CabinetModel cabinetModel);
}

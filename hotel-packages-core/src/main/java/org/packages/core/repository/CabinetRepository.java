package org.packages.core.repository;

import org.hotel.packages.model.models.CabinetModel;
import org.packages.core.model.CabinetQueryCondition;

import java.util.List;

public interface CabinetRepository {

    int save(CabinetModel cabinetModel);

    List<CabinetModel> queryByCondition(CabinetQueryCondition condition);
}

package org.hotel.packages.core.repository;

import org.hotel.packages.core.model.PackageRelationQueryCondition;
import org.hotel.packages.model.models.CabinetPackageRelationModel;

import java.util.List;

public interface CabinetPackageRelationRepository {

    /**
     * 批量保存
     *
     * @param relationModels
     */
    void batchSave(List<CabinetPackageRelationModel> relationModels);

    /**
     * 按条件查询
     *
     * @param condition
     * @return
     */
    List<CabinetPackageRelationModel> queryByCondition(PackageRelationQueryCondition condition);
}

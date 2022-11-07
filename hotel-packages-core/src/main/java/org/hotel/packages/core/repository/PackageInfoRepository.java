package org.hotel.packages.core.repository;

import org.hotel.packages.core.model.PackageQueryCondition;
import org.hotel.packages.model.models.PackageModel;

import java.util.List;

public interface PackageInfoRepository {

    int save(PackageModel packageModel);

    List<PackageModel> queryByCondition(PackageQueryCondition condition);
}

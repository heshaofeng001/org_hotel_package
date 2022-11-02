package org.packages.core.repository;

import org.hotel.packages.model.models.CabinetPackageRelationModel;

import java.util.List;

public interface CabinetPackageRelationRepository {

    int batchSave(List<CabinetPackageRelationModel> relationModels);
}

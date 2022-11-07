package org.hotel.packages.core.repository;

import org.hotel.packages.model.models.CabinetPackageRelationModel;

import java.util.List;

public interface CabinetPackageRelationRepository {

    void batchSave(List<CabinetPackageRelationModel> relationModels);
}

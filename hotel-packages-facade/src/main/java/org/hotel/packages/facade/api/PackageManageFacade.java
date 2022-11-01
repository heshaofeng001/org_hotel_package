package org.hotel.packages.facade.api;

import org.hotel.packages.facade.model.PackageDTO;
import org.hotel.packages.facade.request.PackageCheckInRequest;
import org.hotel.packages.facade.result.Result;

public interface PackageManageFacade {

    Result<PackageDTO> checkIn(PackageCheckInRequest request);
}

package org.hotel.packages.biz.convert;

import org.hotel.packages.facade.model.packages.CabinetDTO;
import org.hotel.packages.model.models.CabinetModel;

public class CabinetConvertor {

    public static CabinetDTO convertToDTO(CabinetModel cabinetModel) {
        CabinetDTO cabinetDTO = new CabinetDTO();
        cabinetDTO.setCabinetId(cabinetModel.getCabinetId());
        return cabinetDTO;
    }
}

package org.hotel.packages.model.models;

import lombok.Data;
import org.hotel.packages.facade.model.ToString;
import org.hotel.packages.model.enums.CabinetStatusEnum;

@Data
public class CabinetModel extends ToString {

    private String cabinetId;

    private CabinetStatusEnum status;

    private String size;

    private String number;
}

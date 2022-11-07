package org.hotel.packages.model.models;

import lombok.Data;
import org.hotel.packages.facade.model.base.ToString;
import org.hotel.packages.facade.model.status.CabinetStatusEnum;

@Data
public class CabinetModel extends ToString {

    private String cabinetId;

    private CabinetStatusEnum status;

    private String size;

    private String number;
}

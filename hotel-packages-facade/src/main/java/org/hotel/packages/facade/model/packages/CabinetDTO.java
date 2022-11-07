package org.hotel.packages.facade.model.packages;

import lombok.Data;
import org.hotel.packages.facade.model.base.BaseDTO;
import org.hotel.packages.facade.model.status.CabinetStatusEnum;

/**
 * 包裹
 */
@Data
public class CabinetDTO extends BaseDTO {

    private String cabinetId;

    private String status;

    private String size;

    private String number;

}

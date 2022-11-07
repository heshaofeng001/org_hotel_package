package org.hotel.packages.facade.request;

import lombok.Data;
import org.hotel.packages.facade.model.packages.PackageDTO;

import java.util.List;

/**
 * @author he_sh
 * @version 2022-11月-05
 **/
@Data
public class CheckInConsultRequest extends BaseRequest{


    private String customerId;

    /**
     * 批量存储
     */
    private List<PackageDTO> packages;
}

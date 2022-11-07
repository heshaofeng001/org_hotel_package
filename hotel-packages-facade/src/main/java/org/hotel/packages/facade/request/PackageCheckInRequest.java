package org.hotel.packages.facade.request;

import lombok.Data;
import org.hotel.packages.facade.model.packages.PackageDTO;

import java.util.List;

@Data
public class PackageCheckInRequest extends BaseRequest{


    private String customerId;

    /**
     * 批量存储
     */
    private List<PackageDTO> packages;

}

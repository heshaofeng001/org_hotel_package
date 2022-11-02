package org.hotel.packages.facade.request;

import lombok.Data;
import org.hotel.packages.facade.model.PackageBaseDTO;

import java.util.List;

@Data
public class PackageCheckInRequest {

    private String customerId;

    /**
     * 批量存储
     */
    private List<PackageBaseDTO> packages;

}

package org.hotel.packages.facade.request;

import lombok.Data;
import org.hotel.packages.facade.model.packages.PackageDTO;

import java.util.List;

/**
 * 此处按customerId查询，customerId查询需要放在web层
 *
 * @author he_sh
 * @version 2022-11月-07
 **/
@Data
public class PackageCheckInRequest extends BaseRequest {


    /**
     * 客户ID
     */
    private String customerId;

    /**
     * 批量存储
     */
    private List<PackageDTO> packages;

}

package org.hotel.packages.facade.request;

import lombok.Data;

import java.util.List;

/**
 * 包裹批量查询接口
 *
 * @author he_sh
 * @version 2022-11月-07
 **/
@Data
public class BatchQueryPackagesRequest extends BaseRequest {

    private String customerId;

    /**
     * 运单号
     */
    private List<String> waybillIds;

    /**
     * 包裹状态
     */
    private String packageStatus;

}

package org.hotel.packages.facade.api;

import org.hotel.packages.facade.model.customer.CustomerDTO;
import org.hotel.packages.facade.request.CustomerCreateRequest;
import org.hotel.packages.facade.request.CustomerQueryRequest;
import org.hotel.packages.facade.result.BatchQueryResult;
import org.hotel.packages.facade.result.Result;

/**
 * 客户管理服务
 *
 * @author he_sh
 * @version 2022-11月-05
 **/
public interface CustomerManageFacade {

    /**
     * 客户注册接口
     *
     * @param request
     * @return
     */
    Result<String> create(CustomerCreateRequest request);


    /**
     * 客户条件查询接口
     *
     * @param customerQueryRequest
     * @return
     */
    BatchQueryResult<CustomerDTO> batchQueryByCondition(CustomerQueryRequest customerQueryRequest);
}

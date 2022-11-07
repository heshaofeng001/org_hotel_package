package org.hotel.packages.facade.api;

import org.hotel.packages.facade.model.customer.CustomerDTO;
import org.hotel.packages.facade.request.CustomerCreateRequest;
import org.hotel.packages.facade.request.CustomerQueryRequest;
import org.hotel.packages.facade.result.BatchQueryResult;
import org.hotel.packages.facade.result.Result;

/**
 * @author he_sh
 * @version 2022-11月-05
 **/
public interface CustomerManageFacade {

    Result<String> create(CustomerCreateRequest request);


    BatchQueryResult<CustomerDTO> batchQueryByCondition(CustomerQueryRequest customerQueryRequest);
}

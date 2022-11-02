package org.hotel.packages.facade.api;

import org.hotel.packages.facade.model.CustomerDTO;
import org.hotel.packages.facade.request.QueryConditionDTO;
import org.hotel.packages.facade.result.BatchQueryResult;
import org.hotel.packages.facade.result.Result;

public interface CustomerManageFacade {

    Result<String> create(CustomerDTO customerDTO);


    BatchQueryResult<CustomerDTO> batchQueryByCondition(QueryConditionDTO conditionDTO);
}

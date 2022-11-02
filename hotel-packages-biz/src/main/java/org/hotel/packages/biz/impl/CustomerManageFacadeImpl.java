package org.hotel.packages.biz.impl;

import org.hotel.packages.biz.convert.CustomerConvertor;
import org.hotel.packages.facade.api.CustomerManageFacade;
import org.hotel.packages.facade.model.CustomerDTO;
import org.hotel.packages.facade.request.QueryConditionDTO;
import org.hotel.packages.facade.result.BatchQueryResult;
import org.hotel.packages.facade.result.Result;
import org.hotel.packages.model.models.CustomerModel;
import org.packages.core.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerManageFacadeImpl implements CustomerManageFacade {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Result<String> create(CustomerDTO customerDTO) {

        Result<String>result = new Result<String>();

        CustomerModel customerModel = CustomerConvertor.convertToModel(customerDTO);
        String customerId = customerRepository.save(customerModel);

        result.setData(customerId);
        return result;
    }

    @Override
    public BatchQueryResult<CustomerDTO> batchQueryByCondition(QueryConditionDTO conditionDTO) {
        return null;
    }
}

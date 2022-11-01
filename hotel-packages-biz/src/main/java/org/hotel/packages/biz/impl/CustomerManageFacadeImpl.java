package org.hotel.packages.biz.impl;

import org.hotel.packages.facade.api.CustomerManageFacade;
import org.hotel.packages.facade.model.CustomerDTO;
import org.hotel.packages.facade.result.Result;
import org.packages.core.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerManageFacadeImpl implements CustomerManageFacade {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Result<String> create(CustomerDTO customerDTO) {
        return null;
    }
}

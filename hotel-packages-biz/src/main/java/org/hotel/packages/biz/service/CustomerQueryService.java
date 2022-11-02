package org.hotel.packages.biz.service;

import org.hotel.packages.biz.convert.CustomerConvertor;
import org.hotel.packages.facade.model.CustomerDTO;
import org.hotel.packages.model.models.CustomerModel;
import org.packages.core.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerQueryService {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * 支持缓存
     *
     * @return
     */
    public CustomerDTO queryByCustomerId(String customerId) {

        CustomerModel customerModel = customerRepository.queryById(customerId);

        return CustomerConvertor.convertToDTO(customerModel);
    }
}

package org.hotel.packages.biz.service;

import org.hotel.packages.biz.convert.CustomerConvertor;
import org.hotel.packages.core.redis.CacheService;
import org.hotel.packages.facade.model.customer.CustomerDTO;
import org.hotel.packages.core.redis.config.CacheObjectConfigEnum;
import org.hotel.packages.model.models.CustomerModel;
import org.hotel.packages.core.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author he_sh
 * @version 2022-11月-05
 **/
@Component
public class CustomerQueryService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CacheService<CustomerModel> cacheService;

    /**
     * 支持缓存
     *
     * @return
     */
    public CustomerDTO queryByCustomerId(String customerId) {

        CustomerModel customerModel = cacheService.get(CacheObjectConfigEnum.CUSTOMER, customerId);
        if (customerModel == null) {
            customerModel = customerRepository.queryById(customerId);
            cacheService.put(CacheObjectConfigEnum.CUSTOMER, customerId, customerModel);
        }
        return customerModel != null ? CustomerConvertor.convertToDTO(customerModel) : null;
    }
}

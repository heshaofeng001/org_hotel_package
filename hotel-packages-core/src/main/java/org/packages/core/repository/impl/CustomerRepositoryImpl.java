package org.packages.core.repository.impl;

import org.hotel.packages.dal.CustomerBOMapper;
import org.hotel.packages.dal.idgenerate.IdGenerate;
import org.hotel.packages.dal.idgenerate.TableEnums;
import org.hotel.packages.dal.po.CustomerBO;
import org.hotel.packages.dal.po.CustomerBOExample;
import org.hotel.packages.model.models.CustomerModel;
import org.packages.core.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class CustomerRepositoryImpl implements CustomerRepository {

    @Autowired
    private IdGenerate idGenerate;

    @Autowired
    private CustomerBOMapper customerBOMapper;

    @Override
    public String save(CustomerModel customer) {
        CustomerBO customerBO = convertToBO(customer);
        String customerId = idGenerate.generateId(TableEnums.HOTEL_CUSTOMER);
        customerBO.setCustomerId(customerId);
        customerBOMapper.insert(customerBO);
        return customerId;
    }


    @Override
    public CustomerModel queryById(String customerId) {

        CustomerBOExample example = new CustomerBOExample();
        example.createCriteria().andCustomerIdEqualTo(customerId);
        List<CustomerBO> customerBOS = customerBOMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(customerBOS)) {
            return null;
        }
        Assert.isTrue(customerBOS.size() == 1);
        return convertToModel(customerBOS.get(0));
    }

    private CustomerModel convertToModel(CustomerBO customerBO) {
        CustomerModel customerModel = new CustomerModel();
        customerModel.setCustomerId(customerBO.getCustomerId());
        customerModel.setCustomerName(customerBO.getCustomerName());
        customerModel.setCheckInDate(customerBO.getCheckInDate());
        customerModel.setCheckOutDate(customerBO.getCheckOutDate());
        return customerModel;
    }

    private CustomerBO convertToBO(CustomerModel customer) {
        CustomerBO customerBO = new CustomerBO();
        if (!StringUtils.isEmpty(customer.getCustomerId())) {
            customerBO.setCustomerId(customer.getCustomerId());
        }

        customerBO.setCustomerStatus(customer.getCustomerStatus().getCode());
        customerBO.setCheckInDate(customer.getCheckInDate());
        return customerBO;
    }
}

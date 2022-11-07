package org.hotel.packages.core.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.hotel.packages.core.repository.CustomerRepository;
import org.hotel.packages.dal.CustomerBOMapper;
import org.hotel.packages.dal.idgenerate.IdGenerate;
import org.hotel.packages.dal.idgenerate.TableEnums;
import org.hotel.packages.dal.po.CustomerBO;
import org.hotel.packages.dal.po.CustomerBOExample;
import org.hotel.packages.facade.model.status.CheckInStatusEnum;
import org.hotel.packages.facade.model.status.CustomerStatusEnum;
import org.hotel.packages.model.models.CustomerModel;
import org.hotel.packages.core.model.CustomerQueryCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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

    @Override
    public List<CustomerModel> queryByCondition(CustomerQueryCondition condition) {

        CustomerBOExample example = new CustomerBOExample();
        CustomerBOExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(condition.getName())) {
            criteria.andCustomerNameLike(condition.getName());
        }
        if (StringUtils.isNotBlank(condition.getIdCardNo())) {
            criteria.andIdCardNoEqualTo(condition.getIdCardNo());
        }
        List<CustomerBO> customerBOS = customerBOMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(customerBOS)) {
            return null;
        }
        return convertToModel(customerBOS);
    }

    @Override
    public int update(CustomerModel customer) {

        CustomerBOExample example = new CustomerBOExample();
        example.createCriteria().andCustomerIdEqualTo(customer.getCustomerId());

        CustomerBO customerBO = convertToBO(customer);
        return customerBOMapper.updateByExampleSelective(customerBO, example);
    }

    private List<CustomerModel> convertToModel(List<CustomerBO> customerBOS) {
        List<CustomerModel> customerModels = new ArrayList<>();
        for (CustomerBO customerBO : customerBOS) {
            customerModels.add(convertToModel(customerBO));
        }
        return customerModels;
    }

    private CustomerModel convertToModel(CustomerBO customerBO) {
        CustomerModel customerModel = new CustomerModel();
        customerModel.setCustomerId(customerBO.getCustomerId());
        customerModel.setIdCardNo(customerBO.getIdCardNo());
        customerModel.setCustomerName(customerBO.getCustomerName());
        customerModel.setCustomerStatus(CustomerStatusEnum.getByCode(customerBO.getCustomerStatus()));
        customerModel.setCheckInStatus(CheckInStatusEnum.getByCode(customerBO.getCheckInStatus()));
        customerModel.setCheckInDate(customerBO.getCheckInDate());
        customerModel.setCheckOutDate(customerBO.getCheckOutDate());
        return customerModel;
    }

    private CustomerBO convertToBO(CustomerModel customer) {
        CustomerBO customerBO = new CustomerBO();
        if (!StringUtils.isEmpty(customer.getCustomerId())) {
            customerBO.setCustomerId(customer.getCustomerId());
        }
        customerBO.setCustomerName(customer.getCustomerName());
        customerBO.setIdCardNo(customer.getIdCardNo());
        customerBO.setCustomerStatus(customer.getCustomerStatus().getCode());

        if (customer.getCheckInStatus() != null) {
            customerBO.setCheckInStatus(customer.getCheckInStatus().getCode());
        }
        customerBO.setCheckInDate(customer.getCheckInDate());
        customerBO.setCheckOutDate(customer.getCheckOutDate());
        return customerBO;
    }
}

package org.hotel.packages.core.repository;

import org.hotel.packages.model.models.CustomerModel;
import org.hotel.packages.core.model.CustomerQueryCondition;

import java.util.List;

public interface CustomerRepository {

    String save(CustomerModel customer);

    CustomerModel queryById(String id);

    List<CustomerModel> queryByCondition(CustomerQueryCondition condition);

    int update(CustomerModel customer);
}

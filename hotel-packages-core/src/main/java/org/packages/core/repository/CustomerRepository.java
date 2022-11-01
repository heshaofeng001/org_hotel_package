package org.packages.core.repository;

import org.hotel.packages.model.models.CustomerModel;

public interface CustomerRepository {

    int save(CustomerModel customer);

    CustomerModel queryById(String id);
}

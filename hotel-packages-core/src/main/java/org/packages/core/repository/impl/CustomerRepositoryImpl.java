package org.packages.core.repository.impl;

import org.hotel.packages.model.models.CustomerModel;
import org.packages.core.repository.CustomerRepository;
import org.springframework.stereotype.Component;

@Component
public class CustomerRepositoryImpl implements CustomerRepository {

    @Override
    public int save(CustomerModel customer) {
        return 0;
    }

    @Override
    public CustomerModel queryById(String id) {
        return null;
    }
}

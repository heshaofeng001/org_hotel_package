package org.hotel.packages.biz.convert;

import org.hotel.packages.facade.model.CustomerDTO;
import org.hotel.packages.model.models.CustomerModel;

public class CustomerConvertor {

    public static CustomerModel convertToModel(CustomerDTO customerDTO) {
        CustomerModel customerModel = new CustomerModel();
        customerModel.setCustomerId(customerDTO.getCustomerId());
        customerModel.setCustomerName(customerDTO.getCustomerName());
        return customerModel;
    }

    public static CustomerDTO convertToDTO(CustomerModel customerModel) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(customerModel.getCustomerId());
        customerDTO.setStatus(customerModel.getCustomerStatus().getCode());
        return customerDTO;
    }
}

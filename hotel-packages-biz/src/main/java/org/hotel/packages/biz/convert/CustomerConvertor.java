package org.hotel.packages.biz.convert;

import org.hotel.packages.facade.model.customer.CustomerDTO;
import org.hotel.packages.facade.model.status.CustomerStatusEnum;
import org.hotel.packages.model.models.CustomerModel;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class CustomerConvertor {

    public static CustomerModel convertToModel(CustomerDTO customerDTO) {
        CustomerModel customerModel = new CustomerModel();
        customerModel.setCustomerId(customerDTO.getCustomerId());
        customerModel.setCustomerName(customerDTO.getCustomerName());
        customerModel.setIdCardNo(customerDTO.getIdCardNo());
        customerModel.setCustomerStatus(CustomerStatusEnum.getByCode(customerDTO.getStatus()));
        return customerModel;
    }

    public static List<CustomerDTO> convertToDTO(List<CustomerModel> models) {
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(models)) {
            for (CustomerModel customerModel : models) {
                customerDTOS.add(convertToDTO(customerModel));
            }
        }
        return customerDTOS;
    }

    public static CustomerDTO convertToDTO(CustomerModel customerModel) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(customerModel.getCustomerId());
        customerDTO.setCustomerName(customerModel.getCustomerName());
        customerDTO.setStatus(customerModel.getCustomerStatus().getCode());
        if (customerModel.getCheckInStatus() != null) {
            customerDTO.setCheckInStatus(customerModel.getCheckInStatus().getCode());
        }
        return customerDTO;
    }
}

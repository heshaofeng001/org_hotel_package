package org.hotel.packages.facade.request;

import lombok.Data;
import org.hotel.packages.facade.model.customer.CustomerDTO;

@Data
public class CustomerCreateRequest extends BaseRequest {

    /**
     * 客户信息
     */
    private CustomerDTO customer;
}

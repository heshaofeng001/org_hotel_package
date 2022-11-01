package org.hotel.packages.facade.model;

import lombok.Data;

@Data
public class CustomerDTO extends BaseDTO {

    private String customerId;

    private String status;
}

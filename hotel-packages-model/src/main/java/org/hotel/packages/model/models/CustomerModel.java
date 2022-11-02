package org.hotel.packages.model.models;

import lombok.Data;
import org.hotel.packages.facade.model.ToString;
import org.hotel.packages.model.enums.CustomerStatusEnum;

import java.util.Date;

@Data
public class CustomerModel extends ToString {

    private String customerId;

    private String customerName;

    private CustomerStatusEnum customerStatus;

    private Date checkInDate;

    private Date checkOutDate;
}

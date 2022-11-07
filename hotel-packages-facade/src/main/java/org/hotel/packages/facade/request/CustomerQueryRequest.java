package org.hotel.packages.facade.request;

import lombok.Data;

@Data
public class CustomerQueryRequest extends BaseRequest {

    private String idCardNo;

    private String customerName;

    private String phone;


}

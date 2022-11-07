package org.hotel.packages.core.model;

import lombok.Data;

@Data
public class CustomerQueryCondition {

    private String phone;

    private String name;

    /**
     * 身份证ID
     */
    private String idCardNo;
}

package org.hotel.packages.facade.request;

import lombok.Data;

@Data
public class CustomerQueryRequest extends BaseRequest {

    /**
     * 身份证ID
     */
    private String idCardNo;

    /**
     * 用户名
     */
    private String customerName;

    /**
     * 手机号
     */
    private String phone;


}

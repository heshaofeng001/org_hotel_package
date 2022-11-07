package org.hotel.packages.facade.model.customer;

import lombok.Data;
import org.hotel.packages.facade.model.base.BaseDTO;

/**
 * 客户登记
 * <LI>如果未注册过，则注册用户信息，然后做checkIn操作</LI>
 * <LI>本系统未实现客户入住等业务，而是简单实现用户信息创建（即用户注册）</LI>
 *
 * <LI>包裹系统：为注册用户提供包裹代收、寄存功能</LI>
 *
 * @author
 */
@Data
public class CustomerDTO extends BaseDTO {

    private String customerId;

    private String status;

    private String customerName;

    /**
     * 身份证号
     */
    private String idCardNo;

    private String checkInStatus;
}

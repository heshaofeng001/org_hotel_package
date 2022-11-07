package org.hotel.packages.facade.model.packages;

import lombok.Data;
import org.hotel.packages.facade.model.base.BaseDTO;

import java.util.Date;

@Data
public class PackageBaseDTO extends BaseDTO {

    /**
     * 标准化大小（以容积为衡量标准）
     * <LI>确保多个货品可以计算总存储空间，并分配存储柜</LI>
     */
    private String packageSize;

    private String reservePhone;

    private Date receiveDate;
}

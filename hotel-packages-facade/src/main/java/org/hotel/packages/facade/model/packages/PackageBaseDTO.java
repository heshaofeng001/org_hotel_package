package org.hotel.packages.facade.model.packages;

import lombok.Data;
import org.hotel.packages.facade.model.base.BaseDTO;

import java.util.Date;

@Data
public class PackageBaseDTO extends BaseDTO {

    /**
     * ��׼����С�����ݻ�Ϊ������׼��
     * <LI>ȷ�������Ʒ���Լ����ܴ洢�ռ䣬������洢��</LI>
     */
    private String packageSize;

    private String reservePhone;

    private Date receiveDate;
}

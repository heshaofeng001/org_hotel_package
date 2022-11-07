package org.hotel.packages.biz.model;

import lombok.Data;
import org.hotel.packages.facade.model.OperateContext;
import org.hotel.packages.model.models.WorkOrderModel;

/**
 * @author he_sh
 * @version 2022-11月-07
 **/
@Data
public abstract class AbstractProcessContext {

    private WorkOrderModel workOrderModel;

    private OperateContext operateContext;
}

package org.hotel.packages.biz.model;

import lombok.Data;
import org.hotel.packages.facade.model.OperateContext;
import org.hotel.packages.facade.model.customer.CustomerDTO;
import org.hotel.packages.facade.model.packages.PackageDTO;
import org.hotel.packages.model.models.WorkOrderModel;

import java.util.List;

/**
 * @author he_sh
 * @version 2022-11月-07
 **/
@Data
public class PackageCheckInProcessContext extends AbstractProcessContext {

    private CustomerDTO customer;

    private List<PackageDTO> packages;

    public static PackageCheckInProcessContext newInstance(WorkOrderModel workOrderModel, CustomerDTO customer, List<PackageDTO> packages, OperateContext operateContext) {
        PackageCheckInProcessContext packageCheckInProcessContext = new PackageCheckInProcessContext();
        packageCheckInProcessContext.setPackages(packages);
        packageCheckInProcessContext.setOperateContext(operateContext);
        packageCheckInProcessContext.setWorkOrderModel(workOrderModel);
        packageCheckInProcessContext.setCustomer(customer);
        return packageCheckInProcessContext;
    }
}

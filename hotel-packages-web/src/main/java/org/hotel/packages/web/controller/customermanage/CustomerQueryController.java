package org.hotel.packages.web.controller.customermanage;

import org.hotel.packages.facade.api.CustomerManageFacade;
import org.hotel.packages.facade.model.customer.CustomerDTO;
import org.hotel.packages.facade.request.CustomerQueryRequest;
import org.hotel.packages.facade.result.BatchQueryResult;
import org.hotel.packages.web.model.GatewayResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerQueryController extends AbstractCustomerController {

    @Autowired
    private CustomerManageFacade customerManageFacade;

    @RequestMapping("/queryByCondition")
    public GatewayResult<List<CustomerDTO>> queryByCondition(String name, String phone) {

        GatewayResult<List<CustomerDTO>> gatewayResult = new GatewayResult<List<CustomerDTO>>();
        CustomerQueryRequest conditionDTO = new CustomerQueryRequest();
        conditionDTO.setCustomerName(name);
        conditionDTO.setPhone(phone);
        BatchQueryResult<CustomerDTO> result = customerManageFacade.batchQueryByCondition(conditionDTO);
        gatewayResult.setSuccess(result.isSuccess());
        gatewayResult.setErrorCode(result.getErrorCode());
        gatewayResult.setData(result.getData());
        return gatewayResult;
    }
}

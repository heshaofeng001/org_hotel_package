package org.hotel.packages.web.controller.customermanage;

import org.hotel.packages.facade.api.CustomerManageFacade;
import org.hotel.packages.facade.model.CustomerDTO;
import org.hotel.packages.facade.request.QueryConditionDTO;
import org.hotel.packages.facade.result.BatchQueryResult;
import org.hotel.packages.web.model.GatewayResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerQueryController {

    @Autowired
    private CustomerManageFacade customerManageFacade;

    public GatewayResult<List<CustomerDTO>> queryByCondition(String name, String phone) {

        GatewayResult<List<CustomerDTO>> gatewayResult = new GatewayResult<List<CustomerDTO>>();
        QueryConditionDTO conditionDTO = new QueryConditionDTO();
        conditionDTO.setCustomerName(name);
        conditionDTO.setPhone(phone);
        BatchQueryResult<CustomerDTO> result = customerManageFacade.batchQueryByCondition(conditionDTO);

        return gatewayResult;
    }
}

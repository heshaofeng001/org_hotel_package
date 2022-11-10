package org.hotel.packages.web.controller.customermanage;

import org.hotel.packages.facade.api.CustomerManageFacade;
import org.hotel.packages.facade.model.customer.CustomerDTO;
import org.hotel.packages.facade.request.CustomerCreateRequest;
import org.hotel.packages.facade.result.Result;
import org.hotel.packages.web.model.GatewayResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class CustomerCheckController extends AbstractCustomerController {

    @Autowired
    private CustomerManageFacade customerManageFacade;

    @RequestMapping("/checkIn")
    public GatewayResult<String> checkIn(String idCardNo, String name) {

        GatewayResult<String> gatewayResult = new GatewayResult<String>();

        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerName(name);
        customerDTO.setIdCardNo(idCardNo);
        customerCreateRequest.setCustomer(customerDTO);
        customerCreateRequest.setRequestId(UUID.randomUUID().toString());
        Result<String> result = customerManageFacade.create(customerCreateRequest);
        retrieveBaseInfo(result, gatewayResult);
        gatewayResult.setData(result.getData());
        return gatewayResult;
    }
}

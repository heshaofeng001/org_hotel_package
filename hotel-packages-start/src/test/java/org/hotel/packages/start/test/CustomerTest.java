package org.hotel.packages.start.test;

import org.apache.commons.lang3.StringUtils;
import org.hotel.packages.facade.api.CustomerManageFacade;
import org.hotel.packages.facade.model.customer.CustomerDTO;
import org.hotel.packages.facade.request.CustomerCreateRequest;
import org.hotel.packages.facade.result.Result;
import org.hotel.packages.start.ApplicationStart;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.Assert.*;

@SpringBootTest(classes = ApplicationStart.class)
@ActiveProfiles("test")
public class CustomerTest extends BaseTest{

    @Autowired
    private CustomerManageFacade customerManageFacade;

    @ParameterizedTest
    @CsvFileSource(resources = "/custertest.csv")
    public void testCreateCustomer(String icCardNo, String customerName, String result, String errorCode) {
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest();
        customerCreateRequest.setRequestId(UUID.randomUUID().toString());

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerName(customerName);
        customerDTO.setIdCardNo(icCardNo);
        customerCreateRequest.setCustomer(customerDTO);
        Result<String> createResult = customerManageFacade.create(customerCreateRequest);

        checkBaseResult(createResult,result,errorCode);

    }

}

package org.hotel.packages.start.test;


import org.apache.commons.lang3.StringUtils;
import org.hotel.packages.facade.api.CustomerManageFacade;
import org.hotel.packages.facade.api.PackageManageFacade;
import org.hotel.packages.facade.model.OperateContext;
import org.hotel.packages.facade.model.customer.CustomerDTO;
import org.hotel.packages.facade.model.packages.CabinetDTO;
import org.hotel.packages.facade.model.packages.PackageDTO;
import org.hotel.packages.facade.request.CheckInConsultRequest;
import org.hotel.packages.facade.request.CustomerQueryRequest;
import org.hotel.packages.facade.request.PackageCheckInRequest;
import org.hotel.packages.facade.result.BatchQueryResult;
import org.hotel.packages.facade.result.Result;
import org.hotel.packages.start.ApplicationStart;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;


@ActiveProfiles("test")
public class PackageManageTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(PackageManageTest.class);

    @Autowired
    private PackageManageFacade packageManageFacade;

    @Autowired
    private CustomerManageFacade customerManageFacade;

    @ParameterizedTest
    @CsvFileSource(resources = "/packagecheckinconsult.csv")
    public void testCheckInConsult(String icCardNo, String customerName, String wayBillId, String packageSize, String result, String errorCode) {
        OperateContext operateContext = createOperateContext(icCardNo, customerName);
        CustomerDTO customerDTO = queryCustomer(icCardNo, customerName, operateContext);

        CheckInConsultRequest checkInConsultRequest = new CheckInConsultRequest();
        checkInConsultRequest.setRequestId(UUID.randomUUID().toString());
        checkInConsultRequest.setCustomerId(customerDTO.getCustomerId());
        checkInConsultRequest.setPackages(createPackages(wayBillId, packageSize));
        checkInConsultRequest.setOperateContext(operateContext);
        Result<String> checkInConsult = packageManageFacade.checkInConsult(checkInConsultRequest);
        log.info(checkInConsult.toString());

        checkBaseResult(checkInConsult, result, errorCode);
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/packagecheckin.csv")
    public void testCheckIn(String icCardNo, String customerName, String wayBillId, String packageSize, String result, String errorCode) {

        OperateContext operateContext = createOperateContext(icCardNo, customerName);
        CustomerDTO customerDTO = queryCustomer(icCardNo, customerName, operateContext);

        PackageCheckInRequest packageCheckInRequest = new PackageCheckInRequest();
        packageCheckInRequest.setRequestId(UUID.randomUUID().toString());
        packageCheckInRequest.setCustomerId(customerDTO.getCustomerId());
        packageCheckInRequest.setPackages(createPackages(wayBillId, packageSize));
        packageCheckInRequest.setOperateContext(operateContext);
        Result<CabinetDTO> checkInResult = packageManageFacade.checkIn(packageCheckInRequest);
        log.info(checkInResult.toString());

        if (Boolean.parseBoolean(result)) {
            assertNotNull(checkInResult.getData());
        }
        checkBaseResult(checkInResult, result, errorCode);
    }


    private CustomerDTO queryCustomer(String idCardNo, String customerName, OperateContext operateContext) {
        CustomerQueryRequest customerQueryRequest = new CustomerQueryRequest();
        customerQueryRequest.setRequestId(UUID.randomUUID().toString());
        customerQueryRequest.setCustomerName(customerName);
        customerQueryRequest.setIdCardNo(idCardNo);
        customerQueryRequest.setOperateContext(operateContext);
        BatchQueryResult<CustomerDTO> queryResult = customerManageFacade.batchQueryByCondition(customerQueryRequest);

        Assert.isTrue(queryResult.isSuccess());
        Assert.notEmpty(queryResult.getData());

        return queryResult.getData().get(0);
    }

    private List<PackageDTO> createPackages(String wayBillId, String packageSize) {
        List<PackageDTO> packageBaseDTOS = new ArrayList<PackageDTO>();
        PackageDTO packageDTO = new PackageDTO();
        packageDTO.setWaybillId(wayBillId);
        packageDTO.setPackageSize(packageSize);
        packageDTO.setReservePhone("15000000000");
        packageDTO.setReceiveDate(new Date());
        packageBaseDTOS.add(packageDTO);
        return packageBaseDTOS;
    }

    private OperateContext createOperateContext(String operateId, String operateType) {
        OperateContext operateContext = new OperateContext();
        operateContext.setOperateId(operateId);
        operateContext.setOperateType(operateType);
        return operateContext;
    }
}

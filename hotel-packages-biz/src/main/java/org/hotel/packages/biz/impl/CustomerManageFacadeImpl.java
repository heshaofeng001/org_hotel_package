package org.hotel.packages.biz.impl;

import org.hotel.packages.biz.convert.CustomerConvertor;
import org.hotel.packages.biz.impl.template.FacadeExecuteTemplate;
import org.hotel.packages.core.model.CustomerQueryCondition;
import org.hotel.packages.core.repository.CustomerRepository;
import org.hotel.packages.facade.api.CustomerManageFacade;
import org.hotel.packages.facade.model.customer.CustomerDTO;
import org.hotel.packages.facade.request.CustomerCreateRequest;
import org.hotel.packages.facade.request.CustomerQueryRequest;
import org.hotel.packages.facade.result.BatchQueryResult;
import org.hotel.packages.facade.result.Result;
import org.hotel.packages.facade.model.status.CheckInStatusEnum;
import org.hotel.packages.facade.model.status.CustomerStatusEnum;
import org.hotel.packages.model.models.CustomerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class CustomerManageFacadeImpl extends FacadeExecuteTemplate implements CustomerManageFacade {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Result<String> create(CustomerCreateRequest request) {

        Result<String> result = new Result<String>();
        doTrans(request, result, new CallBack() {
            @Override
            public void onCall() {

                CustomerModel customerModel = CustomerConvertor.convertToModel(request.getCustomer());
                customerModel.setCustomerStatus(CustomerStatusEnum.EFFECTIVE);

                //登记用户
                registerCustomer(customerModel);

                //入住
                checkIn(customerModel);

                result.setData(customerModel.getCustomerId());
                result.setSuccess(Boolean.TRUE);
            }
        });
        return result;
    }

    @Override
    public BatchQueryResult<CustomerDTO> batchQueryByCondition(CustomerQueryRequest customerQueryRequest) {

        BatchQueryResult<CustomerDTO> result = new BatchQueryResult<CustomerDTO>();
        doTrans(customerQueryRequest, result, new CallBack() {
            @Override
            public void onCall() {
                CustomerQueryCondition condition = new CustomerQueryCondition();
                condition.setIdCardNo(customerQueryRequest.getIdCardNo());
                condition.setName(customerQueryRequest.getCustomerName());
                condition.setPhone(customerQueryRequest.getPhone());
                List<CustomerModel> customerModels = customerRepository.queryByCondition(condition);
                result.setData(CustomerConvertor.convertToDTO(customerModels));
                result.setSuccess(Boolean.TRUE);
            }
        });
        return result;
    }

    /**
     * 注册用户
     * <li>如果已经注册过，则更新信息、否则新建用户</li>
     *
     * @param customerModel
     */
    private void registerCustomer(CustomerModel customerModel) {

        //根据身份证查询用户信息
        CustomerQueryCondition condition = new CustomerQueryCondition();
        condition.setIdCardNo(customerModel.getIdCardNo());
        List<CustomerModel> customerModels = customerRepository.queryByCondition(condition);

        //如果已经注册过，则更新信息、否则新建用户
        if (!CollectionUtils.isEmpty(customerModels)) {
            Assert.isTrue(customerModels.size() == 1);
            CustomerModel existCustomerModel = customerModels.get(0);
            customerModel.setCustomerId(existCustomerModel.getCustomerId());
            customerRepository.update(customerModel);
        } else {
            String customerId = customerRepository.save(customerModel);
            customerModel.setCustomerId(customerId);
        }
    }

    /**
     * 创建用户，并登记入住
     *
     * @return
     */
    private void checkIn(CustomerModel customerModel) {

        //设置入住流水
        customerModel.setCheckInStatus(CheckInStatusEnum.CHECK_IN);
        customerModel.setCheckInDate(new Date());

        customerRepository.update(customerModel);
    }
}

package org.hotel.packages.core.repository.impl;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.hotel.packages.core.model.WorkOrderQueryCondition;
import org.hotel.packages.core.repository.WorkOrderRepository;
import org.hotel.packages.dal.WorkOderBOMapper;
import org.hotel.packages.dal.idgenerate.IdGenerate;
import org.hotel.packages.dal.idgenerate.TableEnums;
import org.hotel.packages.dal.po.WorkOderBO;
import org.hotel.packages.dal.po.WorkOderBOExample;
import org.hotel.packages.facade.model.status.WorkOrderStatusEnum;
import org.hotel.packages.model.enums.WorkOrderTypeEnum;
import org.hotel.packages.model.models.WorkOrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class WorkOrderRepositoryImpl implements WorkOrderRepository {

    @Autowired
    private IdGenerate idGenerate;

    @Autowired
    private WorkOderBOMapper workOderBOMapper;

    @Override
    public int save(WorkOrderModel workOrderModel) {

        WorkOderBO workOderBO = convertToBO(workOrderModel);
        workOderBO.setOrderId(idGenerate.generateId(TableEnums.HOTEL_WORK_ORDER));
        return workOderBOMapper.insert(workOderBO);
    }

    @Override
    public List<WorkOrderModel> queryByCondition(WorkOrderQueryCondition condition) {

        WorkOderBOExample example = new WorkOderBOExample();
        WorkOderBOExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(condition.getRequestId())) {
            criteria.andRequestIdEqualTo(condition.getRequestId());
        }
        if (condition.getType() != null) {
            criteria.andOrderTypeEqualTo(condition.getType().getCode());
        }
        List<WorkOderBO> workOderBOS = workOderBOMapper.selectByExample(example);
        return convertToModel(workOderBOS);
    }

    @Override
    public int update(WorkOrderModel workOrderModel) {
        WorkOderBOExample example = new WorkOderBOExample();
        WorkOderBOExample.Criteria criteria = example.createCriteria();
        criteria.andOrderIdEqualTo(workOrderModel.getOrderId());

        WorkOderBO workOderBO = convertToBO(workOrderModel);
        return workOderBOMapper.updateByExampleSelective(workOderBO, example);
    }

    private WorkOderBO convertToBO(WorkOrderModel workOrderModel) {
        WorkOderBO workOderBO = new WorkOderBO();
        workOderBO.setOrderId(workOrderModel.getOrderId());
        workOderBO.setOrderType(workOrderModel.getOrderType().getCode());
        workOderBO.setStatus(workOrderModel.getStatus().getCode());
        workOderBO.setRequestId(workOrderModel.getRequestId());
        workOderBO.setExtInfo(JSONObject.toJSONString(workOrderModel.getExtInfo()));
        return workOderBO;
    }

    private List<WorkOrderModel> convertToModel(List<WorkOderBO> boList) {
        List<WorkOrderModel> models = new ArrayList<WorkOrderModel>();
        if (!CollectionUtils.isEmpty(boList)) {
            for (WorkOderBO workOderBO : boList) {
                models.add(convertToModel(workOderBO));
            }
        }
        return models;
    }

    private WorkOrderModel convertToModel(WorkOderBO workOderBO) {
        WorkOrderModel workOrderModel = new WorkOrderModel();
        workOrderModel.setOrderId(workOderBO.getOrderId());
        workOrderModel.setOrderType(WorkOrderTypeEnum.getByCode(workOderBO.getOrderType()));
        workOrderModel.setStatus(WorkOrderStatusEnum.getByCode(workOderBO.getStatus()));
        return workOrderModel;
    }
}

package org.hotel.packages.core.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.hotel.packages.core.repository.CabinetRepository;
import org.hotel.packages.dal.CabinetBOMapper;
import org.hotel.packages.dal.idgenerate.IdGenerate;
import org.hotel.packages.dal.idgenerate.TableEnums;
import org.hotel.packages.dal.po.CabinetBO;
import org.hotel.packages.dal.po.CabinetBOExample;
import org.hotel.packages.facade.model.status.CabinetStatusEnum;
import org.hotel.packages.model.models.CabinetModel;
import org.hotel.packages.core.model.CabinetQueryCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class CabinetRepositoryImpl implements CabinetRepository {

    @Autowired
    private IdGenerate idGenerate;

    @Autowired
    private CabinetBOMapper cabinetBOMapper;

    @Override
    public int save(CabinetModel cabinetModel) {

        CabinetBO cabinetBO = convertToBO(cabinetModel);
        cabinetBO.setCabinetId(idGenerate.generateId(TableEnums.HOTEL_CABINET));

        return cabinetBOMapper.insert(cabinetBO);
    }

    @Override
    public List<CabinetModel> queryByCondition(CabinetQueryCondition condition) {

        CabinetBOExample example = new CabinetBOExample();
        CabinetBOExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(condition.getMinSize())) {
            criteria.andSizeGreaterThan(condition.getMinSize());
        }
        if (StringUtils.isNotBlank(condition.getStatus())) {
            criteria.andCabinetStatusEqualTo(condition.getStatus());
        }
        if (!CollectionUtils.isEmpty(condition.getCabinetIds())) {
            criteria.andCabinetIdIn(condition.getCabinetIds());
        }
        List<CabinetBO> boList = cabinetBOMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(boList)) {
            return null;
        }
        List<CabinetModel> cabinetModels = new ArrayList<>();
        for (CabinetBO cabinetBO : boList) {
            cabinetModels.add(convertToModel(cabinetBO));
        }
        return cabinetModels;
    }

    @Override
    public CabinetModel queryByCabinetId(String cabinetId) {
        CabinetBOExample example = new CabinetBOExample();
        CabinetBOExample.Criteria criteria = example.createCriteria();
        criteria.andCabinetIdEqualTo(cabinetId);
        List<CabinetBO> boList = cabinetBOMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(boList)) {
            return null;
        }
        return convertToModel(boList.get(0));
    }

    @Override
    public CabinetModel lock(String cabinetId) {

        //@ TODO 此处自写SQL，实现加锁
        CabinetBOExample example = new CabinetBOExample();
        CabinetBOExample.Criteria criteria = example.createCriteria();
        criteria.andCabinetIdEqualTo(cabinetId);
        List<CabinetBO> cabinetBOS = cabinetBOMapper.selectByExample(example);
        Assert.notEmpty(cabinetBOS, "加锁失败");
        return convertToModel(cabinetBOS.get(0));
    }

    @Override
    public int update(CabinetModel cabinetModel) {

        CabinetBOExample example = new CabinetBOExample();
        CabinetBOExample.Criteria criteria = example.createCriteria();
        criteria.andCabinetIdEqualTo(cabinetModel.getCabinetId());

        CabinetBO cabinetBO = convertToBO(cabinetModel);
        return cabinetBOMapper.updateByExampleSelective(cabinetBO, example);
    }

    private CabinetBO convertToBO(CabinetModel cabinetModel) {
        CabinetBO cabinetBO = new CabinetBO();
        cabinetBO.setCabinetId(cabinetModel.getCabinetId());
        cabinetBO.setCabinetStatus(cabinetModel.getStatus().getCode());
        cabinetBO.setNumber(cabinetModel.getNumber());
        cabinetBO.setSize(cabinetModel.getSize());
        return cabinetBO;
    }

    private CabinetModel convertToModel(CabinetBO cabinetBO) {
        CabinetModel cabinetModel = new CabinetModel();
        cabinetModel.setCabinetId(cabinetBO.getCabinetId());
        cabinetModel.setNumber(cabinetBO.getNumber());
        cabinetModel.setSize(cabinetBO.getSize());
        cabinetModel.setStatus(CabinetStatusEnum.getByCode(cabinetBO.getCabinetStatus()));
        return cabinetModel;
    }
}

package org.hotel.packages.biz.service;

import org.hotel.packages.biz.convert.PackageConvertor;
import org.hotel.packages.facade.model.customer.CustomerDTO;
import org.hotel.packages.facade.model.packages.PackageDTO;
import org.hotel.packages.facade.model.status.PackageStatusEnum;
import org.hotel.packages.model.models.PackageModel;
import org.hotel.packages.core.model.PackageQueryCondition;
import org.hotel.packages.core.repository.PackageInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 包裹登记服务
 * <LI>包裹登记，按运单号幂等登记</LI>
 *
 * @author he_sh
 * @version 2022-11月-05
 **/
@Component
public class PackageEnrollService {

    private static final Logger log = LoggerFactory.getLogger(PackageEnrollService.class);

    @Autowired
    private PackageInfoRepository packageInfoRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    /**
     * 登记包裹
     * <LI>运单号作为唯一ID</LI>
     * <li>根据运单号查询包裹登记情况，未登记，则登记进去，否则返回登记结果</li>
     *
     * @param customer
     * @param packages
     */
    public void enrollPackages(CustomerDTO customer, List<PackageDTO> packages) {

        Assert.notEmpty(packages, "包裹不能为空");

        //1、查询已经登记过的包裹
        List<String> waybillIds = packages.stream().map(packageDTO -> packageDTO.getWaybillId()).collect(Collectors.toList());
        PackageQueryCondition condition = new PackageQueryCondition();
        condition.setOwnerId(customer.getCustomerId());
        condition.setWaybillIds(waybillIds);
        List<PackageModel> orgPackages = packageInfoRepository.queryByCondition(condition);

        //2、未登记的包裹发起登记，登记的回填信息（packageID、status）
        List<PackageDTO> needEnrollPackages = new ArrayList<>();
        Map<String, PackageModel> existedWayIDToPackageMapping = new HashMap<>();
        if (!CollectionUtils.isEmpty(orgPackages)) {
            orgPackages.forEach(packageModel -> existedWayIDToPackageMapping.put(packageModel.getWaybillId(), packageModel));
        }
        for (PackageDTO requestPackageDTO : packages) {
            PackageModel existPackage = existedWayIDToPackageMapping.get(requestPackageDTO.getWaybillId());
            if (existPackage != null) {
                requestPackageDTO.setPackageId(existPackage.getPackageId());
                requestPackageDTO.setPackageStatus(existPackage.getStatus().getCode());
            } else {
                needEnrollPackages.add(requestPackageDTO);
            }
        }

        //3、登记包裹
        enroll(needEnrollPackages);
    }

    /**
     * 登记包裹
     * <li>waybillId+ownerId 为唯一索引</li>
     *
     * @param packages
     */
    private void enroll(List<PackageDTO> packages) {

        if (CollectionUtils.isEmpty(packages)) {
            log.info("没有需要新登记的包裹");
            return;
        }
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {

                for (PackageDTO packageDTO : packages) {
                    PackageModel packageModel = PackageConvertor.convertToModel(packageDTO);
                    packageModel.setStatus(PackageStatusEnum.CHECK_IN);
                    packageInfoRepository.save(packageModel);
                    packageDTO.setPackageId(packageModel.getPackageId());
                }
                return true;
            }
        });

    }
}

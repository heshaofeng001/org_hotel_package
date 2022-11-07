package org.hotel.packages.biz.service;

import org.hotel.packages.biz.convert.CabinetConvertor;
import org.hotel.packages.biz.convert.PackageConvertor;
import org.hotel.packages.core.model.CabinetQueryCondition;
import org.hotel.packages.core.model.PackageQueryCondition;
import org.hotel.packages.core.model.PackageRelationQueryCondition;
import org.hotel.packages.core.repository.CabinetPackageRelationRepository;
import org.hotel.packages.core.repository.CabinetRepository;
import org.hotel.packages.core.repository.PackageInfoRepository;
import org.hotel.packages.facade.model.packages.CabinetDTO;
import org.hotel.packages.facade.model.packages.PackageDTO;
import org.hotel.packages.facade.model.status.CabinetPackageRelationStatusEnum;
import org.hotel.packages.model.models.CabinetModel;
import org.hotel.packages.model.models.CabinetPackageRelationModel;
import org.hotel.packages.model.models.PackageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author he_sh
 * @version 2022-11月-07
 **/
@Component
public class PackageQueryService {

    @Autowired
    private PackageInfoRepository packageInfoRepository;

    @Autowired
    private CabinetPackageRelationRepository cabinetPackageRelationRepository;

    @Autowired
    private CabinetRepository cabinetRepository;

    public List<PackageDTO> query(PackageQueryCondition condition) {

        List<PackageDTO> packageDTOS = new ArrayList<>();

        //1、查询包裹信息
        List<PackageModel> packageModels = packageInfoRepository.queryByCondition(condition);
        if (CollectionUtils.isEmpty(packageModels)) {
            return null;
        }

        Map<String, PackageDTO> packageIdToDTOMapping = new HashMap<String, PackageDTO>();
        List<String> packageIds = packageModels.stream().map(packageModel -> {
            PackageDTO packageDTO = PackageConvertor.convertToDTO(packageModel);
            packageDTOS.add(packageDTO);
            packageIdToDTOMapping.put(packageDTO.getPackageId(), packageDTO);
            return packageModel.getPackageId();
        }).collect(Collectors.toList());


        //2、查询包裹、柜子关联关系
        PackageRelationQueryCondition relationQueryCondition = new PackageRelationQueryCondition();
        relationQueryCondition.setPackageIds(packageIds);
        relationQueryCondition.setRelationStatus(CabinetPackageRelationStatusEnum.EFFECTIVE.getCode());
        List<CabinetPackageRelationModel> relationModels = cabinetPackageRelationRepository.queryByCondition(relationQueryCondition);
        if (CollectionUtils.isEmpty(relationModels)) {
            return packageDTOS;
        }


        //转换出映射关系
        List<String> cabinetIds = new ArrayList<>();
        Map<String, List<String>> cabinetIdToPackageIdMapping = new HashMap<>();
        for (CabinetPackageRelationModel cabinetPackageRelationModel : relationModels) {

            List<String> subPackageIds = cabinetIdToPackageIdMapping.get(cabinetPackageRelationModel.getCabinetId());
            if (!cabinetIds.contains(cabinetPackageRelationModel.getCabinetId())) {
                cabinetIds.add(cabinetPackageRelationModel.getCabinetId());
                subPackageIds = new ArrayList<>();
                cabinetIdToPackageIdMapping.put(cabinetPackageRelationModel.getCabinetId(), subPackageIds);
            }
            subPackageIds.add(cabinetPackageRelationModel.getPackageId());
        }


        //3、查询柜子信息
        CabinetQueryCondition cabinetQueryCondition = new CabinetQueryCondition();
        cabinetQueryCondition.setCabinetIds(cabinetIds);
        List<CabinetModel> cabinetModels = cabinetRepository.queryByCondition(cabinetQueryCondition);
        Assert.notEmpty(cabinetModels);

        //将柜子信息，回填至PackageDTO
        for (CabinetModel cabinetModel : cabinetModels) {
            CabinetDTO cabinetDTO = CabinetConvertor.convertToDTO(cabinetModel);
            List<String> subPackageIds = cabinetIdToPackageIdMapping.get(cabinetDTO.getCabinetId());
            for (String packageId : subPackageIds) {
                PackageDTO packageDTO = packageIdToDTOMapping.get(packageId);
                packageDTO.setCabinet(cabinetDTO);
            }
        }

        return packageDTOS;
    }

}

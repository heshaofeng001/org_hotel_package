package org.hotel.packages.biz.service;

import org.apache.commons.lang3.StringUtils;
import org.hotel.packages.biz.convert.CabinetConvertor;
import org.hotel.packages.core.repository.WorkOrderRepository;
import org.hotel.packages.facade.model.packages.CabinetDTO;
import org.hotel.packages.facade.model.packages.PackageDTO;
import org.hotel.packages.facade.result.Result;
import org.hotel.packages.model.constants.CommonConstants;
import org.hotel.packages.facade.model.status.CabinetPackageRelationStatusEnum;
import org.hotel.packages.facade.model.status.CabinetStatusEnum;
import org.hotel.packages.model.exception.CommonErrorCodeEnum;
import org.hotel.packages.model.models.CabinetModel;
import org.hotel.packages.model.models.CabinetPackageRelationModel;
import org.hotel.packages.model.models.WorkOrderModel;
import org.hotel.packages.core.model.CabinetQueryCondition;
import org.hotel.packages.core.repository.CabinetPackageRelationRepository;
import org.hotel.packages.core.repository.CabinetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 柜子分配服务
 * <LI>分配原则</LI>
 * <pre>
 *     1、查询满足条件（柜子为空状态、柜子的容积大于包裹容积总和）的柜子
 *     2、优先分配容积最小的柜子
 * </pre>
 *
 * <LI>柜子分配具备幂等能力：按workorderID幂等</LI>
 *
 * @author he_sh
 * @version 2022-11月-05
 **/
@Component
public class CabinetAllocateService {

    @Autowired
    private CabinetRepository cabinetRepository;

    @Autowired
    private CabinetPackageRelationRepository cabinetPackageRelationRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private WorkOrderRepository workOrderRepository;

    /**
     * 判断是否有可用柜子
     *
     * @param packages
     * @return
     */
    public boolean allocateAble(List<PackageDTO> packages) {

        return chooseCabinet(packages) != null;
    }

    /**
     * 给工单分配柜子
     *
     * @param workOrderModel
     * @param packages
     * @return
     */
    public Result<CabinetDTO> allocate(WorkOrderModel workOrderModel, List<PackageDTO> packages) {
        Result<CabinetDTO> allocateResult = new Result<CabinetDTO>();

        //1、重复分配校验(已经分配过，则直接返回柜子)
        CabinetDTO allocateCabinetDTO = duplicateCheck(workOrderModel);
        if (allocateCabinetDTO != null) {
            allocateResult.setData(allocateCabinetDTO);
            allocateResult.setSuccess(Boolean.TRUE);
            return allocateResult;
        }
        //2、选择柜子
        final CabinetDTO cabinetDTO = chooseCabinet(packages);
        if (cabinetDTO == null) {
            allocateResult.setSuccess(Boolean.FALSE);
            allocateResult.setErrorCode(CommonErrorCodeEnum.NO_CABINET_AVAILABLE.getCode());
            return allocateResult;
        }

        transactionTemplate.execute(new TransactionCallback<CabinetModel>() {
            @Override
            public CabinetModel doInTransaction(TransactionStatus transactionStatus) {

                //1、锁定货柜，避免并发分配
                CabinetModel oldCabinetModel = cabinetRepository.lock(cabinetDTO.getCabinetId());
                if (!StringUtils.equals(oldCabinetModel.getStatus().getCode(), CabinetStatusEnum.FREEDOM.getCode())) {
                    throw new RuntimeException();
                }
                //2、创建使用信息
                List<CabinetPackageRelationModel> relationModels = createRelation(cabinetDTO, packages);
                cabinetPackageRelationRepository.batchSave(relationModels);

                //3、修改货柜使用中状态，保存分配关联关系
                oldCabinetModel.setStatus(CabinetStatusEnum.USING);
                cabinetRepository.update(oldCabinetModel);
                workOrderModel.getExtInfo().put(CommonConstants.CABINET_ID_KEY, cabinetDTO.getCabinetId());
                workOrderRepository.update(workOrderModel);
                return oldCabinetModel;
            }
        });
        allocateResult.setData(cabinetDTO);
        allocateResult.setSuccess(Boolean.TRUE);
        return allocateResult;
    }

    /**
     * 选择可用柜子
     *
     * @param packages
     * @return
     */
    public CabinetDTO chooseCabinet(List<PackageDTO> packages) {

        //计算包裹的容积总和
        String packageTotalSize = calculatePackageTotalSize(packages);

        //查询容积大于总和，且空闲状态的柜子
        CabinetQueryCondition condition = new CabinetQueryCondition();
        condition.setMinSize(packageTotalSize);
        condition.setStatus(CabinetStatusEnum.FREEDOM.getCode());
        List<CabinetModel> cabinetModels = cabinetRepository.queryByCondition(condition);
        if (CollectionUtils.isEmpty(cabinetModels)) {
            return null;
        }

        //3、按柜子的容积排序，优先使用小的柜子
        cabinetModels.sort(new Comparator<CabinetModel>() {
            @Override
            public int compare(CabinetModel o1, CabinetModel o2) {
                return Integer.parseInt(o1.getSize()) - Integer.parseInt(o2.getSize());
            }
        });
        return CabinetConvertor.convertToDTO(cabinetModels.get(0));
    }


    /**
     * <LI>重复分配校验，如果重复，则返回已经分配的</LI>
     *
     * <LI>@ TODO 此处应该改造成 柜子使用流程，请求ID=workOrderModel，从而便于反差柜子的主人 </LI>
     *
     * @param workOrderModel
     * @return
     */
    private CabinetDTO duplicateCheck(WorkOrderModel workOrderModel) {

        if (CollectionUtils.isEmpty(workOrderModel.getExtInfo()) || StringUtils.isBlank(workOrderModel.getExtInfo().get(CommonConstants.CABINET_ID_KEY))) {
            return null;
        }
        String enrolledCabinetId = workOrderModel.getExtInfo().get(CommonConstants.CABINET_ID_KEY);
        CabinetModel cabinetModel = cabinetRepository.queryByCabinetId(enrolledCabinetId);
        Assert.notNull(cabinetModel, "柜子信息有误");
        return CabinetConvertor.convertToDTO(cabinetModel);
    }

    /**
     * 通过关系，可查询：1、包裹在哪个柜子， 2、柜子中的包裹是谁的
     *
     * @param cabinetDTO
     * @param packages
     * @return
     */
    private List<CabinetPackageRelationModel> createRelation(CabinetDTO cabinetDTO, List<PackageDTO> packages) {

        List<CabinetPackageRelationModel> relationModels = new ArrayList<CabinetPackageRelationModel>();
        for (PackageDTO packageDTO : packages) {
            CabinetPackageRelationModel cabinetPackageRelationModel = new CabinetPackageRelationModel();
            cabinetPackageRelationModel.setCabinetId(cabinetDTO.getCabinetId());
            cabinetPackageRelationModel.setPackageId(packageDTO.getPackageId());
            cabinetPackageRelationModel.setStatus(CabinetPackageRelationStatusEnum.EFFECTIVE.getCode());
            relationModels.add(cabinetPackageRelationModel);
        }
        return relationModels;
    }


    private String calculatePackageTotalSize(List<PackageDTO> packages) {

        int totalSize = 0;
        for (PackageDTO packageDTO : packages) {
            totalSize += Integer.parseInt(packageDTO.getPackageSize());
        }
        return Integer.toString(totalSize);
    }

}

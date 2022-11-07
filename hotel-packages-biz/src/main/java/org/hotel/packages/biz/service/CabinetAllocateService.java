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
 * ���ӷ������
 * <LI>����ԭ��</LI>
 * <pre>
 *     1����ѯ��������������Ϊ��״̬�����ӵ��ݻ����ڰ����ݻ��ܺͣ��Ĺ���
 *     2�����ȷ����ݻ���С�Ĺ���
 * </pre>
 *
 * <LI>���ӷ���߱��ݵ���������workorderID�ݵ�</LI>
 *
 * @author he_sh
 * @version 2022-11��-05
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
     * �ж��Ƿ��п��ù���
     *
     * @param packages
     * @return
     */
    public boolean allocateAble(List<PackageDTO> packages) {

        return chooseCabinet(packages) != null;
    }

    /**
     * �������������
     *
     * @param workOrderModel
     * @param packages
     * @return
     */
    public Result<CabinetDTO> allocate(WorkOrderModel workOrderModel, List<PackageDTO> packages) {
        Result<CabinetDTO> allocateResult = new Result<CabinetDTO>();

        //1���ظ�����У��(�Ѿ����������ֱ�ӷ��ع���)
        CabinetDTO allocateCabinetDTO = duplicateCheck(workOrderModel);
        if (allocateCabinetDTO != null) {
            allocateResult.setData(allocateCabinetDTO);
            allocateResult.setSuccess(Boolean.TRUE);
            return allocateResult;
        }
        //2��ѡ�����
        final CabinetDTO cabinetDTO = chooseCabinet(packages);
        if (cabinetDTO == null) {
            allocateResult.setSuccess(Boolean.FALSE);
            allocateResult.setErrorCode(CommonErrorCodeEnum.NO_CABINET_AVAILABLE.getCode());
            return allocateResult;
        }

        transactionTemplate.execute(new TransactionCallback<CabinetModel>() {
            @Override
            public CabinetModel doInTransaction(TransactionStatus transactionStatus) {

                //1���������񣬱��Ⲣ������
                CabinetModel oldCabinetModel = cabinetRepository.lock(cabinetDTO.getCabinetId());
                if (!StringUtils.equals(oldCabinetModel.getStatus().getCode(), CabinetStatusEnum.FREEDOM.getCode())) {
                    throw new RuntimeException();
                }
                //2������ʹ����Ϣ
                List<CabinetPackageRelationModel> relationModels = createRelation(cabinetDTO, packages);
                cabinetPackageRelationRepository.batchSave(relationModels);

                //3���޸Ļ���ʹ����״̬��������������ϵ
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
     * ѡ����ù���
     *
     * @param packages
     * @return
     */
    public CabinetDTO chooseCabinet(List<PackageDTO> packages) {

        //����������ݻ��ܺ�
        String packageTotalSize = calculatePackageTotalSize(packages);

        //��ѯ�ݻ������ܺͣ��ҿ���״̬�Ĺ���
        CabinetQueryCondition condition = new CabinetQueryCondition();
        condition.setMinSize(packageTotalSize);
        condition.setStatus(CabinetStatusEnum.FREEDOM.getCode());
        List<CabinetModel> cabinetModels = cabinetRepository.queryByCondition(condition);
        if (CollectionUtils.isEmpty(cabinetModels)) {
            return null;
        }

        //3�������ӵ��ݻ���������ʹ��С�Ĺ���
        cabinetModels.sort(new Comparator<CabinetModel>() {
            @Override
            public int compare(CabinetModel o1, CabinetModel o2) {
                return Integer.parseInt(o1.getSize()) - Integer.parseInt(o2.getSize());
            }
        });
        return CabinetConvertor.convertToDTO(cabinetModels.get(0));
    }


    /**
     * <LI>�ظ�����У�飬����ظ����򷵻��Ѿ������</LI>
     *
     * <LI>@ TODO �˴�Ӧ�ø���� ����ʹ�����̣�����ID=workOrderModel���Ӷ����ڷ�����ӵ����� </LI>
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
        Assert.notNull(cabinetModel, "������Ϣ����");
        return CabinetConvertor.convertToDTO(cabinetModel);
    }

    /**
     * ͨ����ϵ���ɲ�ѯ��1���������ĸ����ӣ� 2�������еİ�����˭��
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

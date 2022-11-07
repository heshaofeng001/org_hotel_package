package org.hotel.packages.facade.api;

import org.hotel.packages.facade.model.packages.CabinetDTO;
import org.hotel.packages.facade.model.packages.PackageDTO;
import org.hotel.packages.facade.request.BatchQueryPackagesRequest;
import org.hotel.packages.facade.request.CheckInConsultRequest;
import org.hotel.packages.facade.request.PackageCheckInRequest;
import org.hotel.packages.facade.result.BatchQueryResult;
import org.hotel.packages.facade.result.Result;

/**
 * 包裹管理接口
 *
 * @author he_sh
 * @version 2022-11月-05
 **/
public interface PackageManageFacade {

    /**
     * 包裹是否可以代收咨询接口
     *
     * @return
     */
    Result<String> checkInConsult(CheckInConsultRequest request);

    /**
     * 包裹寄存受理服务
     *
     * <LI>包裹寄放流程</LI>
     * <pre>
     *     1、包裹寄放受理：
     *          包裹寄放校验（客户是否入住状态）、
     *          分配存储柜（根据包裹大小，自动选择存储柜（并锁定））
     *          登记信息
     *     2、包裹存放
     *          存储归使用后，处罚包裹状态变更（已存放）
     *     3、通知用户
     * </pre>
     *
     * <LI>页面交互流程：客户列表页面，选择指定客户后，进入包裹代收界面</LI>
     *
     * @param request
     * @return
     */
    Result<CabinetDTO> checkIn(PackageCheckInRequest request);

    /**
     * 客户包裹查询接口
     *
     * @param request
     * @return
     */
    BatchQueryResult<PackageDTO> batchQueryPackages(BatchQueryPackagesRequest request);
}

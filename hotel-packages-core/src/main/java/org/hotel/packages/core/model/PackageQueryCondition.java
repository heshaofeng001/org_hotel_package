package org.hotel.packages.core.model;

import lombok.Data;
import org.hotel.packages.model.enums.RoleTypeEnum;

import java.util.List;

@Data
public class PackageQueryCondition {

    /**
     * 归属人ID
     */
    private String ownerId;

    /**
     * 归属人类型
     */
    private RoleTypeEnum ownerType;

    /**
     * 运单号
     */
    private List<String> waybillIds;

    /**
     * 包裹状态
     */
    private String packageStatus;
}

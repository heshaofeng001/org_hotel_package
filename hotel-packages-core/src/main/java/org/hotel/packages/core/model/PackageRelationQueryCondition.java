package org.hotel.packages.core.model;

import lombok.Data;

import java.util.List;

/**
 * @author he_sh
 * @version 2022-11月-07
 **/
@Data
public class PackageRelationQueryCondition {

    private List<String> packageIds;

    private String relationStatus;
}

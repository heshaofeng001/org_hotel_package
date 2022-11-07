package org.hotel.packages.core.model;

import lombok.Data;

import java.util.List;

@Data
public class CabinetQueryCondition {

    private List<String> cabinetIds;

    private String minSize;

    private String status;
}

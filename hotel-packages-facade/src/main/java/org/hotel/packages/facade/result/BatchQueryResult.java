package org.hotel.packages.facade.result;

import lombok.Data;

import java.util.List;

@Data
public class BatchQueryResult<T> extends BaseResult {

    private List<T> data;
}

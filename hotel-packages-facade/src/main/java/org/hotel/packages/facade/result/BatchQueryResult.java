package org.hotel.packages.facade.result;

import lombok.Data;
import org.hotel.packages.facade.model.ToString;

import java.util.List;

@Data
public class BatchQueryResult<T> extends BaseResult {

    private List<T> datas;
}

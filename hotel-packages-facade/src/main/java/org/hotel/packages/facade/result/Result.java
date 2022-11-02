package org.hotel.packages.facade.result;

import lombok.Data;
import org.hotel.packages.facade.model.ToString;

@Data
public class Result<T> extends BaseResult {

    private T data;

}

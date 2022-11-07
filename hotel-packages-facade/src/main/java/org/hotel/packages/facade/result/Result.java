package org.hotel.packages.facade.result;

import lombok.Data;

@Data
public class Result<T> extends BaseResult {

    private T data;

}

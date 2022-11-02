package org.hotel.packages.web.model;

import lombok.Data;

@Data
public class GatewayResult<T> {

    private T data;
}

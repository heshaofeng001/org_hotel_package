package org.hotel.packages.web.controller;

import org.hotel.packages.facade.result.BaseResult;
import org.hotel.packages.web.model.GatewayResult;

/**
 * @author he_sh
 * @version 2022-11月-07
 **/
public class BaseController {

    protected void retrieveBaseInfo(BaseResult facadeResult, GatewayResult gatewayResult) {
        gatewayResult.setSuccess(facadeResult.isSuccess());
        gatewayResult.setErrorCode(facadeResult.getErrorCode());
        gatewayResult.setErrorMsg(facadeResult.getErrorMsg());
    }
}

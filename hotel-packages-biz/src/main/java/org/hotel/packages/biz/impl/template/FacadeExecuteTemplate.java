package org.hotel.packages.biz.impl.template;

import org.hotel.packages.facade.request.BaseRequest;
import org.hotel.packages.facade.result.BaseResult;
import org.hotel.packages.model.exception.CommonErrorCodeEnum;
import org.hotel.packages.model.exception.PackageCommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class FacadeExecuteTemplate {

    private static final Logger log = LoggerFactory.getLogger(FacadeExecuteTemplate.class);

    /**
     * 业务处理模板
     *
     * @param request
     * @param result
     * @param callBack
     */
    public void doTrans(BaseRequest request, BaseResult result, CallBack callBack) {

        try {
            validateRequest(request);

            callBack.onCall();

        } catch (PackageCommonException pe) {
            log.error("业务异常", pe);
            result.setSuccess(Boolean.FALSE);
            result.setErrorCode(pe.getErrorCode().getErrorCode());
        } catch (Exception e) {
            log.error("系统未知异常", e);
            result.setSuccess(Boolean.FALSE);
            result.setErrorCode(CommonErrorCodeEnum.SYSTEM_ERROR.getCode());
        }
    }

    /**
     * 校验请求
     * <LI>此处需要支持标签化的校验</LI>
     *
     * @param request
     */
    private void validateRequest(BaseRequest request) {
        Assert.notNull(request);
    }

    public interface CallBack {
        void onCall();
    }
}

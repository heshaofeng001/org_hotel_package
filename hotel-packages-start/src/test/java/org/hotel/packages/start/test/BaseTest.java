package org.hotel.packages.start.test;

import org.apache.commons.lang3.StringUtils;
import org.hotel.packages.facade.result.BaseResult;
import org.hotel.packages.start.ApplicationStart;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

/**
 * @author he_sh
 * @version 2022-11月-06
 **/
@SpringBootTest(classes = ApplicationStart.class)
public class BaseTest {

    protected void checkBaseResult(BaseResult baseResult, String success, String errorCode) {

        assertEquals(success, Boolean.toString(baseResult.isSuccess()));
        if (StringUtils.isNotBlank(errorCode)) {
            assertEquals(errorCode, baseResult.getErrorCode());
        }
    }
}

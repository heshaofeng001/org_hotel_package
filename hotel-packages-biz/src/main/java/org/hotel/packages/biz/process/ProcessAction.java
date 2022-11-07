package org.hotel.packages.biz.process;

import org.hotel.packages.biz.model.AbstractProcessContext;
import org.hotel.packages.facade.model.base.ToString;
import org.hotel.packages.facade.result.Result;

/**
 * @author he_sh
 * @version 2022-11月-07
 **/
public interface ProcessAction<RS extends ToString,AC extends AbstractProcessContext> {

    Result<RS> execute(AC context);
}

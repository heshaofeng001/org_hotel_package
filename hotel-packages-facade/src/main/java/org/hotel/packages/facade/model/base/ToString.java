package org.hotel.packages.facade.model.base;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public abstract class ToString implements Serializable {

    public String toString() {
        return JSONObject.toJSONString(this);
    }
}

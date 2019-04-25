package com.yzspp.sewage.net.base;

import com.yzspp.sewage.utils.GsonConvertUtils;

import java.io.Serializable;

/**
 * Created by LeoCheung4ever on 2018/8/3.
 *
 * @See
 * @Description
 */

public class ResponseBean implements Serializable {

    private static final long serialVersionUID = 0L;

    public String toJSONString() {
        return GsonConvertUtils.toJSONString(this);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + this.toJSONString();
    }
}

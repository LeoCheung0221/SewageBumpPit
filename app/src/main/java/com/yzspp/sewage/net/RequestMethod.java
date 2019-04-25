package com.yzspp.sewage.net;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by 鼠夏目 on 2019/4/25.
 *
 * @See
 * @Description
 */
@IntDef({RequestMethod.METHOD_GET,
        RequestMethod.METHOD_POST,
        RequestMethod.METHOD_DELETE,
        RequestMethod.METHOD_PUT})
@Retention(RetentionPolicy.SOURCE)
public @interface RequestMethod {
    // 请求方式 GET POST DELETE PUT
    int METHOD_GET = 0;
    int METHOD_POST = 1;
    int METHOD_DELETE = 2;
    int METHOD_PUT = 3;
}

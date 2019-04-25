package com.yzspp.sewage.net.base.okhttp;

import java.util.Map;

/**
 * Created by 鼠夏目 on 2018/12/13.
 *
 * @See
 * @Description
 */
public interface HasParamsable {

    OkHttpRequestBuilder params(Map<String, String> params);

    OkHttpRequestBuilder addParam(String key, String val);

}

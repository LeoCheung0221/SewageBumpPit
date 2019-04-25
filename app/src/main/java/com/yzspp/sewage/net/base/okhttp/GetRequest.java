package com.yzspp.sewage.net.base.okhttp;

import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by 鼠夏目 on 2018/12/13.
 *
 * @See
 * @Description
 */
public class GetRequest extends OkHttpRequest {

    public GetRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, int id) {
        super(url, tag, params, headers, id);
    }

    @Override
    protected RequestBody buildRequestBody() {
        return null;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return builder.get().build();
    }
}

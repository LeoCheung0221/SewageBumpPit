package com.yzspp.sewage.net.base.okhttp;

import android.net.Uri;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by 鼠夏目 on 2018/12/13.
 *
 * @See
 * @Description
 */
public class GetBuilder extends OkHttpRequestBuilder<GetBuilder> implements HasParamsable {

    @Override
    public RequestCall build() {
        //添加全局参数
        Map<String, String> addParams = OkHttpUtils.getInstance().getGlobalParams().addParams();
        if (params != null) {
            addParams.putAll(params);
        }

        if (addParams != null) {
            url = appendParams(url, addParams);
        }

        return new GetRequest(url, tag, addParams, headers, id).build();
    }

    protected String appendParams(String url, Map<String, String> params) {
        if (url == null || params == null || params.isEmpty()) {
            return url;
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            builder.appendQueryParameter(key, params.get(key));
        }
        return builder.build().toString();
    }


    @Override
    public GetBuilder params(Map<String, String> params) {
        if (this.params != null) {
            this.params.putAll(params);
        } else {
            this.params = params;
        }
        return this;
    }

    @Override
    public GetBuilder addParam(String key, String val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val);
        return this;
    }


}

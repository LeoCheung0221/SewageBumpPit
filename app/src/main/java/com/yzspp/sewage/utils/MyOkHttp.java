package com.yzspp.sewage.utils;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 网络引擎类
 */

public class MyOkHttp {
    private static MyOkHttp mMyOkHttp;
    private OkHttpClient mClient;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private MyOkHttp() {
        mClient = new OkHttpClient();
    }

    public synchronized static MyOkHttp getInstance() {
        if (mMyOkHttp == null) {
            mMyOkHttp = new MyOkHttp();
        }
        return mMyOkHttp;
    }

    public String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = mClient.newCall(request).execute();
        return response.body().string();
    }


    public String post(String url, HashMap<String, Object> paramMap) throws IOException {
        //设置请求参数
        String json = new Gson().toJson(paramMap);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = mClient.newCall(request).execute();
        return response.body().string();
    }
}

package com.yzspp.sewage.net.base.interceptor;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.yzspp.sewage.net.base.AppConst;
import com.yzspp.sewage.net.base.RequestBean;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import timber.log.Timber;

/**
 * 响应对象拦截器
 */
public class ResponseInterceptor implements Interceptor {

    private static final String TAG = "ResponseInterceptor";
    private static MediaType JSON = MediaType.parse("application/json");

    private Gson parser = new Gson();

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        String url = request.url().toString();

        Response response = chain.proceed(request);
        ResponseBody body = response.body();
        String realString = getRealString(body.bytes());
        if (url.contains(AppConst.API_BASE)) {
            RequestBean result = null;
            try {
                result = parser.fromJson(realString, RequestBean.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String json = new Gson().toJson(result);
            Timber.e(TAG + "  " + "get response body: " + json);
            return response.newBuilder().body(ResponseBody.create(JSON, json)).build();
        } else {
            return response.newBuilder().body(ResponseBody.create(JSON, realString)).build();
        }
    }


    private String getRealString(byte[] data) {
        byte[] h = new byte[2];
        h[0] = (data)[0];
        h[1] = (data)[1];
        int head = getShort(h);
        boolean t = head == 0x1f8b;
        InputStream in = null;
        ByteArrayInputStream bis = null;
        BufferedReader r = null;
        InputStreamReader inputStreamReader = null;
        StringBuilder sb = new StringBuilder();
        try {
            bis = new ByteArrayInputStream(data);
            if (t) {
                in = new GZIPInputStream(bis);
            } else {
                in = bis;
            }

            inputStreamReader = new InputStreamReader(in);
            r = new BufferedReader(inputStreamReader,
                    1000);
            for (String line = r.readLine(); line != null; line = r.readLine()) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (r != null) {
                    r.close();
                }
                if (bis != null) {
                    bis.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return sb.toString();
    }

    private int getShort(byte[] data) {
        return (int) ((data[0] << 8) | data[1] & 0xFF);
    }
}

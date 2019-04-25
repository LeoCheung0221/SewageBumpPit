package com.yzspp.sewage.net.base.interceptor;

import com.yzspp.sewage.utils.NetUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.internal.http.RealResponseBody;

/**
 * Created by LeoCheung4ever on 2018/7/21.
 *
 * @See
 * @Description 网络异常拦截器
 */

public class NetworkInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (NetUtils.isNetworkAvailable())
            return chain.proceed(chain.request());
        else {
            String res = "{\"error\":\"-1200\",\"msg\":\"网络连接失败\",\"nowPage\":\"0\",\"totalPage\":\"0\"}";
            Response response = new Response.Builder()
                    .code(200)
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_1)
                    .body(RealResponseBody.create(MediaType.parse("text"), res.getBytes("utf-8")))
                    .build();
            return response;
        }
    }
}

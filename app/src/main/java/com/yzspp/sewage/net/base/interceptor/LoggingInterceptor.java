package com.yzspp.sewage.net.base.interceptor;

import com.yzspp.sewage.net.base.AppConst;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import timber.log.Timber;

/**
 * Created by LeoCheung4ever on 2018/7/13.
 *
 * @See
 * @Description 自定义log打印拦截器
 */

public class LoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request oldRequest = chain.request();
        Request.Builder newRequestBuild = null;
        String method = oldRequest.method();
        String postBodyString = "";
        if ("POST".equals(method)) {
            RequestBody oldBody = oldRequest.body();
            Headers oldHeaders = oldRequest.headers();
            if (oldBody instanceof FormBody) {
//                FormBody.Builder formBodyBuilder = new FormBody.Builder();
//                formBodyBuilder.add("deviceOs", AppConst.DEVICE_OS);
//                formBodyBuilder.add("appVersion", RxAppTool.getAppVersionName(Core.getInstance()));
//                formBodyBuilder.add("appName", RxAppTool.getAppName(Core.getInstance()));
//                newRequestBuild = oldRequest.newBuilder();
//                RequestBody formBody = formBodyBuilder.build();
//                postBodyString = bodyToString(oldRequest.body());
//                postBodyString += ((postBodyString.length() > 0) ? "&" : "") + bodyToString(formBody);
//                newRequestBuild.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), postBodyString));
            } else if (oldBody instanceof MultipartBody) {
//                MultipartBody oldBodyMultipart = (MultipartBody) oldBody;
//                List<MultipartBody.Part> oldPartList = oldBodyMultipart.parts();
//                MultipartBody.Builder builder = new MultipartBody.Builder();
//                builder.setType(MultipartBody.FORM);
////                RequestBody requestBody1 = RequestBody.create(MediaType.parse("text/plain"), AppConst.DEVICE_OS);
////                RequestBody requestBody2 = RequestBody.create(MediaType.parse("text/plain"), RxAppTool.getAppName(Core.getInstance()));
////                RequestBody requestBody3 = RequestBody.create(MediaType.parse("text/plain"), RxAppTool.getAppVersionName(Core.getInstance()));
//                for (MultipartBody.Part part : oldPartList) {
//                    builder.addPart(part);
//                    postBodyString += (bodyToString(part.body()) + "\n");
//                }
////                postBodyString += (bodyToString(requestBody1) + "\n");
////                postBodyString += (bodyToString(requestBody2) + "\n");
////                postBodyString += (bodyToString(requestBody3) + "\n"); // builder.addPart(oldBody); //不能用这个方法，因为不知道oldBody的类型，可能是PartMap过来的，也可能是多个Part过来的，所以需要重新逐个加载进去
////                builder.addPart(requestBody1);
////                builder.addPart(requestBody2);
////                builder.addPart(requestBody3);
                newRequestBuild = oldRequest.newBuilder();
//                newRequestBuild.post(builder.build());
//                Timber.e(AppConst.APP_GLOBAL_LOG_TAG + "\n" +
//                        "MultipartBody," + oldRequest.url());
            } else {
                newRequestBuild = oldRequest.newBuilder();
                postBodyString = bodyToString(oldRequest.body());
            }
        } else { // 添加新的参数
            HttpUrl.Builder commonParamsUrlBuilder =
                    oldRequest.url().newBuilder().scheme(oldRequest.url().scheme()).host(oldRequest.url().host());
//                            .addQueryParameter("deviceOs", AppConst.DEVICE_OS)
//                            .addQueryParameter("appVersion", RxAppTool.getAppVersionName(Core.getInstance()))
//                            .addQueryParameter("appName", RxAppTool.getAppName(Core.getInstance()));
            newRequestBuild = oldRequest.newBuilder().method(oldRequest.method(), oldRequest.body()).url(commonParamsUrlBuilder.build());
        }
        Request newRequest = newRequestBuild.build();
        long startTime = System.currentTimeMillis();
        Response response = chain.proceed(newRequest);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        int httpStatus = response.code();
        String logSB = "----------Start----------" + "\n|" +
                "Headers:" + "\n|" +
                newRequest.headers().toString() + "\n|" +
                newRequest.toString() + "\n|" +
                (method.equalsIgnoreCase("POST") ? "post参数-> " + postBodyString + "\n|" : "") +
                "httpCode=" + httpStatus + "\n|" +
                "Response:" + content + "\n|" +
                "响应时间:" + +duration + "毫秒" + "\n" +
                "----------End------------";
        Timber.e(AppConst.APP_GLOBAL_LOG_TAG + "\n" + logSB);
        return response.newBuilder().body(okhttp3.ResponseBody.create(mediaType, content)).build();
    }

    private static String bodyToString(final RequestBody request) {
        try {
            final Buffer buffer = new Buffer();
            if (request != null) request.writeTo(buffer);
            else return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}

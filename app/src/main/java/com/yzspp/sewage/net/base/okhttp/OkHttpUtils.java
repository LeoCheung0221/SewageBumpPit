package com.yzspp.sewage.net.base.okhttp;

import android.content.Context;

import com.yzspp.sewage.net.base.interceptor.NetworkInterceptor;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by 鼠夏目 on 2018/12/13.
 *
 * @See
 * @Description
 */
public class OkHttpUtils {

    public static final long DEFAULT_MILLISECONDS = 10_000L;
    private volatile static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Platform mPlatform;
    private Context mContext;

    public OkHttpUtils(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        } else {
            mOkHttpClient = okHttpClient;
        }

        mPlatform = Platform.get();
    }

    public static OkHttpUtils initClient(OkHttpClient okHttpClient) {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtils getInstance() {
        return initClient(null);
    }

    public OkHttpUtils addInterceptor(Interceptor interceptor) {
        if (mOkHttpClient != null && interceptor != null) {
            mOkHttpClient = mOkHttpClient.newBuilder().addInterceptor(interceptor).build();
        }
        return this;
    }

    public static GetBuilder get() {
        return new GetBuilder();
    }

    public OkHttpUtils addInterceptors(List<Interceptor> interceptors) {
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                addInterceptor(interceptor);
            }
        }
        return this;
    }

    public OkHttpUtils init(Context context) {
        mContext = context;

        //添加网络异常拦截器
        if (mOkHttpClient != null) {
            mOkHttpClient = mOkHttpClient.newBuilder().addInterceptor(new NetworkInterceptor()).build();
        }

        return this;
    }

    public OkHttpUtils timeout(long timeout) {
        if (mOkHttpClient != null) {
            mOkHttpClient = mOkHttpClient.newBuilder()
                    .connectTimeout(timeout, TimeUnit.MILLISECONDS)
                    .readTimeout(timeout, TimeUnit.MILLISECONDS)
                    .writeTimeout(timeout, TimeUnit.MILLISECONDS)
                    .build();
        }
        return this;
    }

    public Executor getDelivery() {
        return mPlatform.defaultCallbackExecutor();
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public void execute(final RequestCall requestCall, Callback callback) {
        if (callback == null)
            callback = Callback.CALLBACK_DEFAULT;
        final Callback finalCallback = callback;
        final int id = requestCall.getOkHttpRequest().getId();

        requestCall.getCall().enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                sendFailResultCallback(call, null, e, finalCallback, id);
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                try {
                    if (call.isCanceled()) {
                        sendFailResultCallback(call, response, new IOException("Canceled!"), finalCallback, id);
                        return;
                    }

                    if (!finalCallback.validateResponse(response, id)) {
                        sendFailResultCallback(call, response, new IOException("request failed , reponse's code is : " + response.code()), finalCallback, id);
                        return;
                    }

                    Object o = finalCallback.parseNetworkResponse(response, id);
                    sendSuccessResultCallback(o, finalCallback, id);
                } catch (Exception e) {
                    sendFailResultCallback(call, response, e, finalCallback, id);
                } finally {
                    if (response.body() != null)
                        response.body().close();
                }

            }
        });
    }

    public void sendFailResultCallback(final Call call, final Response response, final Exception e, final Callback callback, final int id) {
        if (callback == null) return;

        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onError(call, response, e, id);
                callback.onAfter(id);
            }
        });
    }

    public void sendSuccessResultCallback(final Object object, final Callback callback, final int id) {
        if (callback == null) return;
        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(object, id);
                callback.onAfter(id);
            }
        });
    }

    public void cancelTag(Object tag) {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    public Context getContext() {
        if (mContext == null) {
            throw (new NullPointerException("必须在application中进行init初始化"));
        }
        return mContext;
    }

    private GlobalParams mGlobalParams = new GlobalParams() {
        @Override
        public Map<String, String> addParams() {
            return new LinkedHashMap<>();
        }
    };

    public GlobalParams getGlobalParams() {
        return mGlobalParams;
    }

    public OkHttpUtils setGlobalParams(GlobalParams globalParams) {
        mGlobalParams = globalParams;
        return this;
    }

}

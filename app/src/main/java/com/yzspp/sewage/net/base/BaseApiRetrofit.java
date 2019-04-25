package com.yzspp.sewage.net.base;

import android.support.annotation.NonNull;

import com.yzspp.sewage.base.Core;
import com.yzspp.sewage.net.base.interceptor.LoggingInterceptor;
import com.yzspp.sewage.net.base.interceptor.ResponseInterceptor;
import com.yzspp.sewage.net.base.persistentcookiejar.ClearableCookieJar;
import com.yzspp.sewage.net.base.persistentcookiejar.PersistentCookieJar;
import com.yzspp.sewage.net.base.persistentcookiejar.cache.SetCookieCache;
import com.yzspp.sewage.net.base.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.yzspp.sewage.utils.NetUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @描述 配置Retrofit（配置网络缓存cache、配置持久cookie免登录）
 */

public class BaseApiRetrofit {

    private final OkHttpClient mClient;

    public OkHttpClient getClient() {
        return mClient;
    }

    public BaseApiRetrofit() {

        /*================== common ==================*/
        //cache
        File httpCacheDir = new File(Core.getContext().getCacheDir(), "response");
        int cacheSize = 10 * 1024 * 1024;// 10 MiB
        Cache cache = new Cache(httpCacheDir, cacheSize);

        //cookie
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(Core.getContext()));

        Interceptor interceptorResponse = new ResponseInterceptor();
        Interceptor interceptorLogger = new LoggingInterceptor();

        //OkHttpClient
        mClient = new OkHttpClient.Builder()
                .addInterceptor(REWRITE_HEADER_CONTROL_INTERCEPTOR)
                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .addInterceptor(interceptorLogger)
                .retryOnConnectionFailure(true)
                .cache(cache)
                .cookieJar(cookieJar)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    //header配置
    Interceptor REWRITE_HEADER_CONTROL_INTERCEPTOR = new HeaderInterceptor();

    //cache配置
    Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new InnerCacheInterceptor();

    private class HeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/json;charset=UTF-8")
                    .build();
            return chain.proceed(request);
        }
    }

    private class InnerCacheInterceptor implements Interceptor {
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            //通过 CacheControl 控制缓存数据
            CacheControl.Builder cacheBuilder = new CacheControl.Builder();
            cacheBuilder.maxAge(0, TimeUnit.SECONDS);//这个是控制缓存的最大生命时间
            cacheBuilder.maxStale(365, TimeUnit.DAYS);//这个是控制缓存的过时时间
            CacheControl cacheControl = cacheBuilder.build();

            //设置拦截器
            Request request = chain.request();
            if (!NetUtils.isNetworkAvailable()) {
                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build();
            }

            Response originalResponse = chain.proceed(request);
            if (NetUtils.isNetworkAvailable()) {
                int maxAge = 0;//read from cache
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28;//tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    }
}

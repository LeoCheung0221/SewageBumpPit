package com.yzspp.sewage.net;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yzspp.sewage.net.api.Api_Biz;
import com.yzspp.sewage.net.base.AppConst;
import com.yzspp.sewage.net.base.BaseApiRetrofit;
import com.yzspp.sewage.net.base.ResponseBean;
import com.yzspp.sewage.net.base.WrapObserver;
import com.yzspp.sewage.net.base.convert.SNTGsonConverterFactory;
import com.yzspp.sewage.net.entity.LoginResp;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import timber.log.Timber;

/**
 * @描述 使用Retrofit对网络请求进行配置
 */
public class ApiRetrofit extends BaseApiRetrofit {

    private static ApiRetrofit mInstance;

    private static Retrofit mRetrofit;

    private Api_Biz mBizApi;

    private ApiRetrofit() {
        super();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .setLenient()
                .create();

        //在构造方法中完成对Retrofit接口的初始化
        Retrofit.Builder mRetrofitBuilder0 = new Retrofit.Builder();
        mRetrofit = mRetrofitBuilder0
                .baseUrl(AppConst.API_BASE)
                .client(getClient())
                .addConverterFactory(SNTGsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        this.mBizApi = mRetrofit.create(Api_Biz.class);
    }

    public static ApiRetrofit getInstance() {
        if (mInstance == null) {
            synchronized (ApiRetrofit.class) {
                if (mInstance == null) {
                    mInstance = new ApiRetrofit();
                }
            }
        }
        return mInstance;
    }

    private RequestBody getRequestBody(HashMap<String, Object> hashMap) {
        if (hashMap == null)
            hashMap = new HashMap<>();
        String route = new Gson().toJson(hashMap);
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), route);
    }

    /* ~~~~~~~~~~~~~~~~~~~~~ 个人账户接口 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

    /**
     * 登录获取验证码
     */
    public void login(HashMap<String, Object> hashMap,WrapObserver<LoginResp> observer) {
        mBizApi.login(getRequestBody(hashMap))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}

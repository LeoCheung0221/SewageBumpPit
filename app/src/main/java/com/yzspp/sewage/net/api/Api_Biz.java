package com.yzspp.sewage.net.api;


import com.yzspp.sewage.net.base.ResponseBean;
import com.yzspp.sewage.net.entity.LoginResp;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @描述 业务接口
 */

public interface Api_Biz {


//    //商品分类
//    @GET("api/m/frontCategory/query")
//    Observable<List<PrdFrontCateResponse>> getPrdCategoryData();
//
//    //某分类下的产品列表
//    @GET("api/m/sku/query")
//    Observable<List<PrdChildCateResponse>> getPrdCategoryChildData(@Query("categoryId") String categoryId, @Query("skuStatus") String skuStatus, @Query("pageSize") String pageSize, @Query("currentPage") String currentPage);
//
//    //查询组合商品列表
//    @GET("api/m/sku/queryPackage")
//    Observable<List<PrdCombinationResponse>> getPrdCombinationData(@Query("thePackageSku") String thePackageSku, @Query("skuStatus") String skuStatus, @Query("pageSize") String pageSize, @Query("currentPage") String currentPage);

    //登录接口
    @POST("userInfo/login")
    Observable<LoginResp> login(@Body RequestBody body);

}

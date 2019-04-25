package com.yzspp.sewage.net.base;

import com.yzspp.sewage.base.Core;

import java.io.File;

/**
 * Created by LeoCheung4ever on 2018/7/25.
 *
 * @See
 * @Description 开发环境切换
 */

public interface Api {

    //默认缓存
    int DEFAULT_CACHE_SIZE = 150 * 1024 * 1024; // 50 MiB

    /*超时时间-默认10秒*/
    int DEFAULT_CONNECT_TIMEOUT = 20;
    int DEFAULT_READ_TIMEOUT = 20;
    int DEFAULT_WRITE_TIMEOUT = 20;

    /*有网情况下的本地缓存时间默认60秒*/
    int DEFAULT_COOKIE_NETWORK_TIME = 0;
    /*无网络的情况下本地缓存时间默认30天*/
    int DEFAULT_COOKIE_NO_NETWORK_TIME = 24 * 60 * 60 * 30;

    //缓存路径
    String PATH_DATA = Core.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";
    String PATH_CACHE = PATH_DATA + "/NetCache";

}

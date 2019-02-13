package com.yzspp.sewage.base;

import android.app.Application;

import com.instabug.library.Instabug;
import com.instabug.library.invocation.InstabugInvocationEvent;
import com.yzspp.sewage.R;

import top.wefor.circularanim.CircularAnim;

/**
 * 程序入口
 */
public class Core extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Instabug
        new Instabug.Builder(this, "4178243dc541d4bedac5548f8ce58355")
                .setInvocationEvent(InstabugInvocationEvent.SHAKE)
                .build();
        //初始化bmob
//        Bmob.initialize(this, "eb5dc119e33ffd6f68d4ee6e6bfae698");
        //ripple动画配置
        CircularAnim.init(700, 500, R.color.colorPrimary);
    }
}

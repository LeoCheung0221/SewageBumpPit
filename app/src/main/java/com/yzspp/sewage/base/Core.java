package com.yzspp.sewage.base;

import android.content.Context;

import com.vondear.rxtool.RxAppTool;
import com.vondear.rxtool.RxTool;
import com.yzspp.sewage.R;

import frame.BaseApp;
import top.wefor.circularanim.CircularAnim;

/**
 * 程序入口
 */
public class Core extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        initRxToast();
        //ripple动画配置
        CircularAnim.init(700, 500, R.color.colorPrimary);
    }

    @Override
    protected void initTinkerPatch() {

    }

    @Override
    protected String getAppNameFromSub() {
        return RxAppTool.getAppName(mContext);
    }

    private void initRxToast() {
        RxTool.init(this);
    }

    public static Context getInstance() {
        return getContext();
    }
}

package com.yzspp.sewage.Feature;

import android.os.Bundle;

import com.yzspp.sewage.R;
import com.yzspp.sewage.base.BaseActivity;

public class VideoWaterActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_water;
    }

    @Override
    protected void initView() {
        initToolbar("视频监控点", true);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {

    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    @Override
    protected String[] getNeedPermissions() {
        return new String[0];
    }
}

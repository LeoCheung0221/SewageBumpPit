package com.yzspp.sewage.Discover;

import android.os.Bundle;

import com.yzspp.sewage.R;
import com.yzspp.sewage.base.BaseActivity;

public class BumpDetailActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bump_detail;
    }

    @Override
    protected void initView() {
        initToolbar("扬子河泵坑监测画面", true);
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

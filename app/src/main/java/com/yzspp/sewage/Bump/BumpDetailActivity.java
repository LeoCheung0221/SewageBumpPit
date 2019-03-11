package com.yzspp.sewage.Bump;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yzspp.sewage.R;
import com.yzspp.sewage.base.BaseActivity;
import com.yzspp.sewage.widget.ExcelLayout;

import frame.tool.MyToast;

public class BumpDetailActivity extends BaseActivity {

    private NestedScrollView elDataView;
    private ImageView ivBigLandScapeView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bump_detail;
    }

    @Override
    protected void initView() {
        initToolbar("扬子河泵坑监测画面", true);

        elDataView = findViewById(R.id.elDataView);
        ivBigLandScapeView = findViewById(R.id.ivBigLandScapeView);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {//横屏
//            MyToast.success(getCurrentActivity(), "横屏");
            //需要执行的操作，可以不写
            elDataView.setVisibility(View.GONE);
            ivBigLandScapeView.setVisibility(View.VISIBLE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {//竖屏
            //需要执行的操作，可以不写
//            MyToast.success(getCurrentActivity(), "竖屏");
            elDataView.setVisibility(View.VISIBLE);
            ivBigLandScapeView.setVisibility(View.GONE);
        }
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

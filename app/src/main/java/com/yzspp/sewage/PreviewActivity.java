package com.yzspp.sewage;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzspp.sewage.Account.LoginActivity;
import com.yzspp.sewage.Discover.BumpListActivity;
import com.yzspp.sewage.Discover.MapRepairActivity;
import com.yzspp.sewage.Feature.BumpManagerActivity;
import com.yzspp.sewage.Feature.RainFallLevelActivity;
import com.yzspp.sewage.Feature.VideoWaterActivity;
import com.yzspp.sewage.Feature.WaterLeachingReportActivity;
import com.yzspp.sewage.Mine.SettingsActivity;
import com.yzspp.sewage.Tools.SSIntentTool;
import com.yzspp.sewage.base.BaseActivity;

public class PreviewActivity extends BaseActivity implements View.OnClickListener {

    private FrameLayout mMapInfoLayoutl;
    private FrameLayout mManagerLayout;
    private FrameLayout mVideoLayout;
    private FrameLayout mRainfallLayout;
    private FrameLayout mReportLayout;
    private ImageView mGoSetting;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_preview;
    }

    @Override
    protected void initView() {
        mMapInfoLayoutl = findViewById(R.id.map_layout);
        mManagerLayout = findViewById(R.id.manager_layout);
        mVideoLayout = findViewById(R.id.video_layout);
        mRainfallLayout = findViewById(R.id.rainfall_layout);
        mReportLayout = findViewById(R.id.report_layout);
        mGoSetting = findViewById(R.id.iv_go_setting);

        mMapInfoLayoutl.setOnClickListener(this);
        mManagerLayout.setOnClickListener(this);
        mVideoLayout.setOnClickListener(this);
        mRainfallLayout.setOnClickListener(this);
        mReportLayout.setOnClickListener(this);
        mGoSetting.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.map_layout:
                SSIntentTool.start(PreviewActivity.this, MapRepairActivity.class);
                break;
            case R.id.manager_layout:
                SSIntentTool.start(PreviewActivity.this, BumpListActivity.class);
                break;
            case R.id.video_layout:
                SSIntentTool.start(PreviewActivity.this, VideoWaterActivity.class);
                break;
            case R.id.rainfall_layout:
                SSIntentTool.start(PreviewActivity.this, RainFallLevelActivity.class);
                break;
            case R.id.report_layout:
                SSIntentTool.start(PreviewActivity.this, WaterLeachingReportActivity.class);
                break;
            case R.id.iv_go_setting:
                SSIntentTool.start(PreviewActivity.this, SettingsActivity.class);
                break;
        }
    }
}

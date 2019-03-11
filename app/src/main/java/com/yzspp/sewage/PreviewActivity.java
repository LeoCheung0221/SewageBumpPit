package com.yzspp.sewage;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yzspp.sewage.Bump.BumpOverviewActivity;
import com.yzspp.sewage.Bump.MapHomePageActivity;
import com.yzspp.sewage.Feature.RainFallLevelActivity;
import com.yzspp.sewage.Feature.VideoWaterActivity;
import com.yzspp.sewage.Feature.WaterLeachingReportActivity;
import com.yzspp.sewage.Mine.SettingsActivity;
import com.yzspp.sewage.utils.SSIntentTool;
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
            case R.id.map_layout: //泵站地图
                SSIntentTool.start(PreviewActivity.this, MapHomePageActivity.class);
                break;
            case R.id.manager_layout: //泵站信息管理
                SSIntentTool.start(PreviewActivity.this, BumpOverviewActivity.class);
                break;
            case R.id.video_layout: //视频监控点
                SSIntentTool.start(PreviewActivity.this, VideoWaterActivity.class);
                break;
            case R.id.rainfall_layout: //重点水位/雨量信息
                SSIntentTool.start(PreviewActivity.this, RainFallLevelActivity.class);
                break;
            case R.id.report_layout: //报警报告
                SSIntentTool.start(PreviewActivity.this, WaterLeachingReportActivity.class);
                break;
            case R.id.iv_go_setting: //设置
                SSIntentTool.start(PreviewActivity.this, SettingsActivity.class);
                break;
        }
    }
}

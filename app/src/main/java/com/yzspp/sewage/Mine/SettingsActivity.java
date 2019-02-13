package com.yzspp.sewage.Mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yzspp.sewage.R;
import com.yzspp.sewage.base.BaseActivity;

import de.hdodenhof.circleimageview.CircleImageView;
import frame.tool.MyPrefence;

public class SettingsActivity extends BaseActivity {

    private CircleImageView civhead;
    private TextView tvusername;
    private RelativeLayout rluserinfo;
    private static final int REQUEST_EDIT_INFO = 607;
    private MyPrefence mPrefence;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initView() {
        initToolbar("设置", true);
        this.rluserinfo = (RelativeLayout) findViewById(R.id.rl_user_info);
        this.tvusername = (TextView) findViewById(R.id.tv_username);
        this.civhead = (CircleImageView) findViewById(R.id.civ_head);

    }

    @Override
    protected void setListener() {
        rluserinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(SettingsActivity.this, EditPersonalInfoActivity.class),
                        REQUEST_EDIT_INFO);
            }
        });
    }

    @Override
    protected void initData() {
        mPrefence = MyPrefence.getInstance(SettingsActivity.this);
    }

    @Override
    protected void setData() {
        showUserInfo();
    }

    /**
     * 显示用户信息
     */
    private void showUserInfo() {
        String path = mPrefence.getString(getString(R.string.avater_path), null);
        if (path != null) {
            Glide.with(this)
                    .load(path)
                    .into(civhead);
        }

    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    @Override
    protected String[] getNeedPermissions() {
        return new String[0];
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT_INFO) {
            if (resultCode == Activity.RESULT_OK) {
                showUserInfo();
            }
        }
    }
}

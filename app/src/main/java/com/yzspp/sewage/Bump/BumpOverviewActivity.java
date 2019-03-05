package com.yzspp.sewage.Bump;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.yzspp.sewage.Bump.frag.BumpDetailFragment;
import com.yzspp.sewage.Bump.frag.DirectoryFragment;
import com.yzspp.sewage.Bump.frag.MeFragment;
import com.yzspp.sewage.Bump.frag.OverViewFragment;
import com.yzspp.sewage.R;
import com.yzspp.sewage.base.BaseActivity;
import com.yzspp.sewage.view.FloatButton;


public class BumpOverviewActivity extends BaseActivity implements View.OnClickListener, FloatButton.OnNaviClickListener {

    private FloatButton fb_overview;
    private FloatButton fb_directory;
    private FloatButton fb_detail;
    private FloatButton fb_me;

    final FragmentManager manager = getSupportFragmentManager();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bump_overview;
    }

    @Override
    protected void initView() {
        fb_overview = findViewById(R.id.navi_overview);
        fb_directory = findViewById(R.id.navi_directory);
        fb_detail = findViewById(R.id.navi_detail);
        fb_me = findViewById(R.id.navi_me);

        initFragment();
    }

    private void initFragment() {
        switchActTitle(0);
        FragmentTransaction transaction = manager.beginTransaction();
        OverViewFragment overViewFragment = OverViewFragment.newInstance();
        transaction.replace(R.id.flContainer, overViewFragment).commit();
    }

    @Override
    protected void setListener() {
        fb_overview.setNaviClickListener(this);
        fb_directory.setNaviClickListener(this);
        fb_detail.setNaviClickListener(this);
        fb_me.setNaviClickListener(this);
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
    public void onClick(View v) {

    }

    private void switchActTitle(int pageIndex) {
        String title;
        switch (pageIndex) {
            case 0:
                title = "扬中市截污泵坑集中监视";
                break;
            case 1:
                title = "扬中市截污泵坑集中监视";
                break;
            case 2:
                title = "XXXXXXX泵站";
                break;
            case 3:
                title = "我的";
                break;
            default:
                title = "总览";
                break;
        }
        initToolbar(title, true);
    }

    @Override
    public void click(View v) {
        Fragment fragment = null;
        FragmentTransaction transaction = null;
        switch (v.getId()) {
            case R.id.fb_overview:
                switchActTitle(0);
                transaction = manager.beginTransaction();
                fragment = OverViewFragment.newInstance();
                break;
            case R.id.fb_directory:
                switchActTitle(1);
                transaction = manager.beginTransaction();
                fragment = DirectoryFragment.newInstance();
                break;
            case R.id.fb_detail:
                switchActTitle(2);
                transaction = manager.beginTransaction();
                fragment = BumpDetailFragment.newInstance();
                break;
            case R.id.fb_me:
                switchActTitle(3);
                transaction = manager.beginTransaction();
                fragment = MeFragment.newInstance();
                break;
        }
        assert fragment != null;
        transaction.replace(R.id.flContainer, fragment).commit();
    }
}

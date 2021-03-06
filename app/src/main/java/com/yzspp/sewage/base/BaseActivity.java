package com.yzspp.sewage.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.wang.avi.AVLoadingIndicatorView;
import com.yzspp.sewage.R;

import java.util.Random;

import frame.permission.CheckPermissionsActivity;
import frame.tool.MyToast;
import frame.tool.NetWorkUtils;

public abstract class BaseActivity extends CheckPermissionsActivity {
    private static final String TAG = "BaseActivity";
    private FrameLayout mContentFl;
    private LinearLayout mEmptyLl;
    private LinearLayout mLoadFailLl;
    private AVLoadingIndicatorView mLoadingIndicatorView;
    private LinearLayout mLoadingLl;
    private TextView mRetryTv;
    private View mRootView;

    private Activity mActivity;

    protected void onCreate(@Nullable Bundle paramBundle) {
        super.onCreate(paramBundle);
        Bundle localBundle = getIntent().getExtras();
        if (localBundle != null)
            getBundleExtras(localBundle);
        setContentView(R.layout.layout_base);
        this.mContentFl = findViewById(R.id.fl);
        this.mEmptyLl = findViewById(R.id.layout_empty);
        this.mLoadFailLl = findViewById(R.id.layout_load_fail);
        this.mRetryTv = findViewById(R.id.tv_retry);
        this.mRetryTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                BaseActivity.this.onRetryClick();
            }
        });
        this.mLoadingLl = findViewById(R.id.layout_loading);
        this.mLoadingIndicatorView = findViewById(R.id.avl);
        setIndicator();
        View localView = getLayoutInflater().inflate(getLayoutId(), null);
//        ButterKnife.bind(this);
        if (localView.findViewById(R.id.toolbar) == null) {
            this.mRootView = localView;
        } else {
            this.mRootView = localView.findViewById(R.id.view_root);
        }
        mActivity = this;
        localView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.mContentFl.addView(localView);
        initView();
        setListener();
        initData();
        setData();
    }


    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void setListener();

    protected abstract void initData();

    protected abstract void setData();

    protected abstract void getBundleExtras(Bundle bundle);

    public void initToolbar(String title, boolean isDisplayHome) {
        setSupportActionBar(findViewById(R.id.toolbar));
        ActionBar localActionBar = getSupportActionBar();
        if (localActionBar != null) {
            localActionBar.setDisplayShowTitleEnabled(false);
            localActionBar.setDisplayHomeAsUpEnabled(true);
            ((TextView) findViewById(R.id.toolbar_title)).setText(title);
            localActionBar.setDisplayHomeAsUpEnabled(isDisplayHome);
        }
    }

    public void initToolbar(String title, boolean isDisplayHome, boolean isShowRightIcon) {
        setSupportActionBar(findViewById(R.id.toolbar));
        ActionBar localActionBar = getSupportActionBar();
        if (localActionBar != null) {
            localActionBar.setDisplayShowTitleEnabled(false);
            localActionBar.setDisplayHomeAsUpEnabled(true);
            ((TextView) findViewById(R.id.toolbar_title)).setText(title);
            localActionBar.setDisplayHomeAsUpEnabled(isDisplayHome);
            if (isShowRightIcon)
                ((ImageView) findViewById(R.id.iv_right_icon)).setImageResource(R.drawable.ic_add);
//                localActionBar.setIcon(R.drawable.ic_add);
        }
    }

    protected void initeActionbar(int titleId, boolean isDisplayHome) {
        initeActionbar(getString(titleId), isDisplayHome);
    }

    protected void initeActionbar(String title, boolean isDisplayHome) {
        ActionBar localActionBar = getSupportActionBar();
        if (localActionBar != null) {
            localActionBar.setTitle(title);
            localActionBar.setDisplayHomeAsUpEnabled(isDisplayHome);
        }
    }

    protected void loadFail() {
        this.mLoadingIndicatorView.smoothToHide();
        this.mLoadingLl.setVisibility(View.GONE);
        this.mRootView.setVisibility(View.GONE);
        this.mEmptyLl.setVisibility(View.GONE);
        this.mLoadFailLl.setVisibility(View.VISIBLE);
    }

    protected void loading() {
        this.mRootView.setVisibility(View.GONE);
        this.mLoadFailLl.setVisibility(View.GONE);
        this.mEmptyLl.setVisibility(View.GONE);
        this.mLoadingLl.setVisibility(View.VISIBLE);
        this.mLoadingIndicatorView.smoothToShow();
    }

    protected void onRetryClick() {
        loading();
    }

    protected void loadComplete() {
        this.mLoadingIndicatorView.smoothToHide();
        this.mLoadingLl.setVisibility(View.GONE);
        this.mLoadFailLl.setVisibility(View.GONE);
        this.mEmptyLl.setVisibility(View.GONE);
        this.mRootView.setVisibility(View.VISIBLE);
        Animation localAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        this.mRootView.setAnimation(localAnimation);
    }

    protected void showEmpty() {
        this.mLoadingIndicatorView.smoothToHide();
        this.mLoadingLl.setVisibility(View.GONE);
        this.mLoadFailLl.setVisibility(View.GONE);
        this.mRootView.setVisibility(View.GONE);
        this.mEmptyLl.setVisibility(View.VISIBLE);
    }

    protected boolean checkInternet() {
        boolean isConnected = NetWorkUtils.isNetworkConnected(this);
        if (!isConnected)
            MyToast.info(this, R.string.no_internet, Toast.LENGTH_SHORT);
        return isConnected;
    }

    public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
        if (paramMenuItem.getItemId() == android.R.id.home)
            finish();
        return true;
    }

    private void setIndicator() {
        String[] arrayOfString = getResources().getStringArray(R.array.arr_indicator);
        int i = new Random().nextInt(arrayOfString.length);
        this.mLoadingIndicatorView.setIndicator(arrayOfString[i]);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    protected Activity getCurrentActivity(){
        return mActivity;
    }
}
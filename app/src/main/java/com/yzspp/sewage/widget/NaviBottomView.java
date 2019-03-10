package com.yzspp.sewage.widget;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;

import com.yzspp.sewage.R;

/**
 * Created by 鼠夏目 on 2019/3/4.
 *
 * @See 底部导航栏
 * @Description
 */
public class NaviBottomView extends ConstraintLayout implements View.OnClickListener {

    private NaviBottomCallback mCallback;

    public NaviBottomView(Context context) {
        super(context);
    }

    public NaviBottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NaviBottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViewById(R.id.navi_overview).setOnClickListener(this);
        findViewById(R.id.navi_directory).setOnClickListener(this);
        findViewById(R.id.navi_detail).setOnClickListener(this);
        findViewById(R.id.navi_me).setOnClickListener(this);
    }

    public void setCallback(NaviBottomCallback callback) {
        this.mCallback = callback;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.navi_overview:
                if (mCallback != null) {
                    mCallback.onOverviewClick();
                }
                break;
            case R.id.navi_directory:
                if (mCallback != null) {
                    mCallback.onDirectoryClick();
                }
                break;
            case R.id.navi_detail:
                if (mCallback != null) {
                    mCallback.onDetailClick();
                }
                break;
            case R.id.navi_me:
                if (mCallback != null) {
                    mCallback.onMeClick();
                }
                break;
        }
    }

    public static interface NaviBottomCallback {

        void onOverviewClick();

        void onDirectoryClick();

        void onDetailClick();

        void onMeClick();
    }
}

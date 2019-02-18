package com.yzspp.sewage.view;

import android.view.View;

import java.util.Calendar;

/**
 * author：leo on 2017/10/22 0022 03:05
 * email： leocheung4ever@gmail.com
 * description: 防止过快点击造成多次点击问题
 * what & why is modified:
 */
public abstract class ForbidQuickClickListener implements View.OnClickListener {

    public static final int MIN_CLICK_TIME = 1000;
    private long lastTime = 0;

    @Override
    public void onClick(View view) {
        long cTime = Calendar.getInstance().getTimeInMillis();
        if (cTime - lastTime > MIN_CLICK_TIME) {
            lastTime = cTime;
            forbidClick(view);
        }
    }

    protected abstract void forbidClick(View view);

}

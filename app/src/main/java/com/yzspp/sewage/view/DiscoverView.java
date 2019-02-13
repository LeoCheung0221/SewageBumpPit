package com.yzspp.sewage.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzspp.sewage.R;


/**
 * 功能详细描述
 */
public class DiscoverView extends FrameLayout {


    private final ImageView mIvIcon;
    private final TextView mTvName;

    public DiscoverView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View v= LayoutInflater.from(context)
                .inflate(R.layout.view_discover, this);
        mIvIcon = findViewById(R.id.iv_icon);
        mTvName = findViewById(R.id.tv_name);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                        attrs,
                        R.styleable.DescoverView,
                        0, 0);

        try {
            mTvName.setText(a.getString(R.styleable.DescoverView_dv_name));
            mIvIcon.setImageResource(a.getResourceId(R.styleable.DescoverView_dv_icon, R.mipmap.ic_launcher));
        } finally {
            a.recycle();
        }
    }

}

package com.yzspp.sewage.widget;

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
 * 一句话功能描述
 * 功能详细描述
 */

public class ResourceItem extends FrameLayout {

    private ImageView ivicon;
    private TextView tvname;
    private TextView tvinfo;

    public ResourceItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_resource, this);
        this.tvinfo = (TextView) view.findViewById(R.id.tv_info);
        this.tvname = (TextView) view.findViewById(R.id.tv_name);
        this.ivicon = (ImageView) view.findViewById(R.id.iv_icon);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ResourceItem,
                0, 0);

        try {
            tvname.setText(a.getString(R.styleable.ResourceItem_ri_name));
            tvinfo.setText(a.getString(R.styleable.ResourceItem_ri_info));
            ivicon.setImageResource(a.getResourceId(R.styleable.ResourceItem_ri_icon,
                    R.mipmap.ic_launcher));

        } finally {
            a.recycle();
        }
    }
}

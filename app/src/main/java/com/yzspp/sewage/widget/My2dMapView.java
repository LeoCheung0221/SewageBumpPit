package com.yzspp.sewage.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.amap.api.maps2d.MapView;


/**
 * author：leo on 2016/10/31 16:46
 * email： leocheung4ever@gmail.com
 * description: 自定义map view 为了去掉 logo
 * what & why is modified:
 */

public class My2dMapView extends MapView {

    private Context mContext;

    public My2dMapView(Context context) {
        super(context);
        initializeView(context);
    }

    public My2dMapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initializeView(context);
    }

    public My2dMapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initializeView(context);
    }

    private void initializeView(Context context) {
        this.mContext = context;
        //View加载完成时回调
        this.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        ViewGroup child = (ViewGroup) getChildAt(0); //地图框架
                        if (child != null && child.getChildAt(2) != null)
                            child.getChildAt(2).setVisibility(View.GONE); //logo
//                        child.getChildAt(7).setVisibility(View.GONE); //缩放
                    }
                }
        );
    }
}

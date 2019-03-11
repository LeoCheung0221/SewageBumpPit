package com.yzspp.sewage.utils;

import android.graphics.Typeface;
import android.widget.TextView;

/**
 * 字体工具类
 */

public class TypefaceUtils {
    private static Typeface typeface;

    public static void setTypeface(TextView textView) {
//        if (typeface == null)
//            typeface = Typeface.createFromAsset(textView.getContext().getAssets(), "fonts/xiyuanjian.ttf");
//        if (textView != null) {
//            textView.setTypeface(typeface);
//        }
    }

    public static void setText(TextView textView, String text) {
//        if (typeface == null)
//            typeface = Typeface.createFromAsset(textView.getContext().getAssets(), "fonts/xiyuanjian.ttf");
        if (textView != null) {
//            textView.setTypeface(typeface);
            textView.setText(text);
        }
    }
}

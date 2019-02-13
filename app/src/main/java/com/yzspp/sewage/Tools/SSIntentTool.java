package com.yzspp.sewage.Tools;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.yzspp.sewage.base.BaseConstant;


/**
 * Created by LeoCheung4ever on 2018/7/14.
 *
 * @See
 * @Description 处理意图跳转工具类 参数一律以Bundle形式传参
 */

public class SSIntentTool {

    public SSIntentTool() {
    }

    public static void start(Context packageContext, Class<?> className) {
        Intent intent = new Intent(packageContext, className);
        ComponentName cn = new ComponentName(packageContext, className);
        intent.setComponent(cn);
        packageContext.startActivity(intent);
    }

    public static void start(Context packageContext, Class<?> className, Bundle bundle) {
        Intent intent = new Intent(packageContext, className);
        if (bundle != null) {
            intent.putExtras(bundle);
        }

        ComponentName cn = new ComponentName(packageContext, className);
        intent.setComponent(cn);
        packageContext.startActivity(intent);
    }

    public static void start(Context packageContext, Class<?> className, String param) {
        Intent intent = new Intent(packageContext, className);
        intent.putExtra(BaseConstant.DATA, param);

        ComponentName cn = new ComponentName(packageContext, className);
        intent.setComponent(cn);
        packageContext.startActivity(intent);
    }

    public static void start(Context packageContext, Class<?> className, boolean param) {
        Intent intent = new Intent(packageContext, className);
        intent.putExtra(BaseConstant.DATA, param);

        ComponentName cn = new ComponentName(packageContext, className);
        intent.setComponent(cn);
        packageContext.startActivity(intent);
    }

    public static void startForResult(Context packageContext, Class<?> className, int requestCode) {
        Intent intent = new Intent(packageContext, className);
        ComponentName cn = new ComponentName(packageContext, className);
        intent.setComponent(cn);
        ((Activity) packageContext).startActivityForResult(intent, requestCode);
    }

    public static void startFragmentForResult(Fragment f, Class<?> className, int requestCode) {
        Intent intent = new Intent(f.getContext(), className);
        ComponentName cn = new ComponentName(f.getContext(), className);
        intent.setComponent(cn);
        f.startActivityForResult(intent, requestCode);
    }

    public static void startForResult(Context packageContext, Class<?> className, Bundle bundle, int requestCode) {
        Intent intent = new Intent(packageContext, className);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        ComponentName cn = new ComponentName(packageContext, className);
        intent.setComponent(cn);
        ((Activity) packageContext).startActivityForResult(intent, requestCode);
    }

    public static void startForResult(Context packageContext, Class<?> className, String param, int requestCode) {
        Intent intent = new Intent(packageContext, className);
        intent.putExtra(BaseConstant.DATA, param);
        ComponentName cn = new ComponentName(packageContext, className);
        intent.setComponent(cn);
        ((Activity) packageContext).startActivityForResult(intent, requestCode);
    }

    public static void startFragmentForResult(Fragment f, Context packageContext, Class<?> className, String param, int requestCode) {
        Intent intent = new Intent(packageContext, className);
        intent.putExtra(BaseConstant.DATA, param);
        ComponentName cn = new ComponentName(packageContext, className);
        intent.setComponent(cn);
        f.startActivityForResult(intent, requestCode);
    }

    public static void startFragmentForResult(Fragment f, Context packageContext, Class<?> className, Bundle bundle, int requestCode) {
        Intent intent = new Intent(packageContext, className);
        intent.putExtras(bundle);
        ComponentName cn = new ComponentName(packageContext, className);
        intent.setComponent(cn);
        f.startActivityForResult(intent, requestCode);
    }

    public static void skipTargetActivity(Context packageContext, Class<?> className) {
        Intent intent = new Intent(packageContext, className);
        ComponentName cn = new ComponentName(packageContext, className);
        intent.setComponent(cn);
        packageContext.startActivity(intent);
    }
}

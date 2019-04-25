package com.yzspp.sewage.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.yzspp.sewage.base.Core;
import com.yzspp.sewage.net.base.AppConst;
import com.yzspp.sewage.net.entity.LoginResp;

import timber.log.Timber;

/**
 * Created by LeoCheung4ever on 2018/7/21.
 *
 * @See
 * @Description 首选项存储工具类
 */

public class SharedPreUtils {

    private SharedPreferences sp;
    private static SharedPreUtils mInstance;

    private final String TAG = "SharedPreUtils";

    private SharedPreUtils() {
        this.sp = PreferenceManager.getDefaultSharedPreferences(Core.getInstance().getApplicationContext());
    }

    public static SharedPreUtils getInstance() {
        if (mInstance == null)
            mInstance = new SharedPreUtils();
        return mInstance;
    }

    public void putContent(String tag, String content) {
        putString(tag, content);
    }

    public String getContent(String tag) {
        return getString(tag);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        String value = sp.getString(key, "");
        return value;
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        Timber.e("SP put ###" + value);
        editor.apply();
    }

    public int getInt(String key) {
        int value = sp.getInt(key, -1);
        return value;
    }

    public int getIntZero(String key) {
        int value = sp.getInt(key, 0);

        Timber.e("SP###" + value);
        return value;
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public long getLong(String key) {
        long value = sp.getLong(key, -1L);
        return value;
    }

    public void putFloat(String key, float value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public float getFloat(String key) {
        float value = sp.getFloat(key, -1.0F);
        return value;
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key) {
        boolean value = sp.getBoolean(key, false);
        return value;
    }

    public void remove(String key) {
        sp.edit().remove(key).apply();
    }

    public void putJSONCache(String key, String content) {
        SharedPreferences sp = Core.getInstance().getSharedPreferences("JSON_CACHE", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, content);
        editor.apply();
    }

    public String readJSONCache(String key) {
        SharedPreferences sp = Core.getInstance().getSharedPreferences("JSON_CACHE", 0);
        String jsoncache = sp.getString(key, (String) null);
        return jsoncache;
    }

    /**
     * 业务常量存储
     **/
    public void setLoginInfo(LoginResp resp) {
        if (resp.getResult()) {
            setLogin(true);
            setUserName(resp.getCurrentUser().getUserName());
            setUserPwd(resp.getCurrentUser().getUserPassword());
        } else {
            setLogin(false);
            setUserName(null);
            setUserPwd(null);
        }
    }

    public void setLogin(boolean isLogin) {
        sp.edit().putBoolean(AppConst.LOGIN_INFO_STATUS, isLogin).apply();
    }

    public boolean isLogin() {
        return sp.getBoolean(AppConst.LOGIN_INFO_STATUS, false);
    }

    public void setUserName(String name) {
        sp.edit().putString(AppConst.LOGIN_INFO_USERNAME, name).apply();
    }

    public String getUserName() {
        return sp.getString(AppConst.LOGIN_INFO_USERNAME, "");
    }

    public void setUserPwd(String pwd) {
        sp.edit().putString(AppConst.LOGIN_INFO_USERPWD, pwd).apply();
    }

    public String getUserPwd() {
        return sp.getString(AppConst.LOGIN_INFO_USERPWD, "");
    }
}

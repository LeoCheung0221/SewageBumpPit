package frame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;
import android.util.Log;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by LeoCheung4ever on 2018/7/11.
 *
 * @See
 * @Description 该基类不使用Dagger框架
 */

public abstract class BaseApp extends Application {

    /**
     * 维护Activity 的list
     */
    private static final List<Activity> mActivitys = Collections.synchronizedList(new LinkedList<Activity>());

    //以下属性应用于整个应用程序，合理利用资源，减少资源浪费
    protected static Context mContext;//上下文
    protected static Thread mMainThread;//主线程
    protected static long mMainThreadId;//主线程id
    protected static Looper mMainLooper;//循环队列
    protected static Handler mHandler;//主线程Handler

    protected static String appName;
    private Application mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        initTinkerPatch();
        mApplication = this;
        //对全局属性赋值
        mContext = getApplicationContext();
        mMainThread = Thread.currentThread();
        mMainThreadId = android.os.Process.myTid();
        mHandler = new Handler();

        appName = getAppNameFromSub();
        registerActivityListener();

        initThirdLibs();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    protected Application getApplication(){
        return mApplication;
    }

    protected abstract void initTinkerPatch();

    /**
     * @param activity 作用说明 ：添加一个activity到管理里
     */
    public void pushActivity(Activity activity) {
        mActivitys.add(activity);
        Timber.e("activityList:size: " + mActivitys.size());
    }

    /**
     * @param activity 作用说明 ：删除一个activity在管理里
     */
    public void popActivity(Activity activity) {
        mActivitys.remove(activity);
        Timber.e("activityList:size: " + mActivitys.size());
    }

    /**
     * get current Activity 获取当前Activity（栈中最后一个压入的）
     */
    public static Activity currentActivity() {
        if (mActivitys == null || mActivitys.isEmpty()) {
            return null;
        }
        Activity activity = mActivitys.get(mActivitys.size() - 1);
        return activity;
    }

    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    public static void finishCurrentActivity() {
        if (mActivitys == null || mActivitys.isEmpty()) {
            return;
        }
        Activity activity = mActivitys.get(mActivitys.size() - 1);
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public static void finishActivity(Activity activity) {
        if (mActivitys == null || mActivitys.isEmpty()) {
            return;
        }
        if (activity != null) {
            mActivitys.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        if (mActivitys == null || mActivitys.isEmpty()) {
            return;
        }
        for (Activity activity : mActivitys) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 按照指定类名找到activity
     *
     * @param cls
     * @return
     */
    public static Activity findActivity(Class<?> cls) {
        Activity targetActivity = null;
        if (mActivitys != null) {
            for (Activity activity : mActivitys) {
                if (activity.getClass().equals(cls)) {
                    targetActivity = activity;
                    break;
                }
            }
        }
        return targetActivity;
    }

    /**
     * @return 作用说明 ：获取当前最顶部activity的实例
     */
    public Activity getTopActivity() {
        Activity mBaseActivity = null;
        synchronized (mActivitys) {
            final int size = mActivitys.size() - 1;
            if (size < 0) {
                return null;
            }
            mBaseActivity = mActivitys.get(size);
        }
        return mBaseActivity;

    }

    /**
     * @return 作用说明 ：获取当前最顶部的activity 名字
     */
    public String getTopActivityName() {
        Activity mBaseActivity = null;
        synchronized (mActivitys) {
            final int size = mActivitys.size() - 1;
            if (size < 0) {
                return null;
            }
            mBaseActivity = mActivitys.get(size);
        }
        return mBaseActivity.getClass().getName();
    }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        if (mActivitys == null) {
            return;
        }
        for (Activity activity : mActivitys) {
            activity.finish();
        }
        mActivitys.clear();
    }

    /**
     * 退出应用程序
     */
    public static void appExit() {
        try {
            Log.e("appExit", "app exit");
            finishAllActivity();
        } catch (Exception e) {
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private void registerActivityListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    /**
                     *  监听到 Activity创建事件 将该 Activity 加入list
                     */
                    pushActivity(activity);

                }

                @Override
                public void onActivityStarted(Activity activity) {

                }

                @Override
                public void onActivityResumed(Activity activity) {

                }

                @Override
                public void onActivityPaused(Activity activity) {

                }

                @Override
                public void onActivityStopped(Activity activity) {

                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    if (null == mActivitys || mActivitys.isEmpty()) {
                        return;
                    }
                    if (mActivitys.contains(activity)) {
                        /**
                         *  监听到 Activity销毁事件 将该Activity 从list中移除
                         */
                        popActivity(activity);
                    }
                }
            });
        }
    }

    protected abstract String getAppNameFromSub();

    protected void initThirdLibs() {

    }

    /**
     * 重启当前应用
     */
    public static void restart() {
        Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(intent);
    }

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context mContext) {
        BaseApp.mContext = mContext;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static void setMainThread(Thread mMainThread) {
        BaseApp.mMainThread = mMainThread;
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

    public static void setMainThreadId(long mMainThreadId) {
        BaseApp.mMainThreadId = mMainThreadId;
    }

    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    public static void setMainThreadLooper(Looper mMainLooper) {
        BaseApp.mMainLooper = mMainLooper;
    }

    public static Handler getMainHandler() {
        return mHandler;
    }

    public static void setMainHandler(Handler mHandler) {
        BaseApp.mHandler = mHandler;
    }
}

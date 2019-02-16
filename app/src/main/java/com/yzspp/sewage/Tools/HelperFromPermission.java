package com.yzspp.sewage.Tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;


import com.yzspp.sewage.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HelperFromPermission {

    public static final int PAY_MAX = 10000; //最大支付金额限制
    public static final String PAY_MAX_MESSAGE = "最大输入金额为" + PAY_MAX + "元，请重新填写";

    public static int getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }


    private static int BASE_MINUTE = 59;  //最小显示分钟数


    @SuppressLint("NewApi")
    public static Bitmap getHttpImageBM(String urlpath)
            throws Exception {
        URL url = new URL(urlpath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5 * 1000);
        Bitmap bitmap = null;
        Log.i("----tencent------", "SHARING_CANCLE" + conn);
        Log.i("----tencent------", "SHARING_CANCLE" + conn.getResponseCode());
        if (conn.getResponseCode() == 200) {
            Log.i("----tencent------", "SHARING_CANCLE");
            InputStream inputStream = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            Log.i("----tencent------", "SHARING_CANCLE" + bitmap);
        }

        return bitmap;
    }

    public static String getUnixTimeString() {
        return System.currentTimeMillis() + "";
    }

    /***
     * 获取相对时间
     * @param time 时间戳
     * @return
     */
    public static String getRelativeTime(String time) {
        try {
            Date date = new Date(Long.parseLong(time) * 1000);
            if ((getNowYear() == getYearFromTime(date) && (getNowMonth() == getMonthFromTime(date)) && (getNowDay() == getDayFromTime(date)))) {
                //同一年同一月同一天时间处理（今天）
                if ((getNowHour() == getHourFromTime(date)) && (getNowMinute() == getMinuteFromTime(date))) {
                    return "刚刚";
                } else if (getNowHour() == getHourFromTime(date)) {
                    //同一个小时内
                    if ((getNowHour() - getHourFromTime(date)) < BASE_MINUTE) {
                        return (getNowMinute() - getMinuteFromTime(date)) + "分钟前";
                    } else {
                        return new SimpleDateFormat("今天 HH:mm").format(date).toString();
                    }
                } else {
                    //不是同一个小时
                    if ((getNowHour() - getHourFromTime(date)) == 1) {

                        if ((getNowMinute() + 60 - getMinuteFromTime(date)) < BASE_MINUTE) {
                            return (getNowMinute() + 60 - getMinuteFromTime(date)) + "分钟前";
                        } else {
                            return new SimpleDateFormat("今天 HH:mm").format(date).toString();
                        }
                    } else {
                        return new SimpleDateFormat("今天 HH:mm").format(date).toString();
                    }
                }
            } else if (isTodayAndYesterday(Calendar.getInstance().getTime(), date)) {
                //今天 和 昨天
                return new SimpleDateFormat("昨天 HH:mm").format(date).toString();
                /*if(getNowHour() == 0 && getHourFromTime(date) == 23){
                    if(getNowMinute() < BASE_MINUTE){
                        return (getNowMinute() + 60 - getMinuteFromTime(date)) + "分钟前";
                    }else{
                        return new SimpleDateFormat("昨天 HH:mm").format(date).toString();
                    }
                }else{
                    return new SimpleDateFormat("昨天 HH:mm").format(date).toString();
                }*/
            } else if ((getNowYear() == getYearFromTime(date))) {
                //今年
                return new SimpleDateFormat("MM月dd日 HH:mm").format(date).toString();
            } else {
                return new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(date).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    /***
     * 判断两个日期是否是今天和昨天
     * @param nowDate
     * @param oldDate
     * @return
     */
    private static boolean isTodayAndYesterday(Date nowDate, Date oldDate) {
        try {
            Calendar calendar_old = Calendar.getInstance();
            calendar_old.setTime(oldDate);
            calendar_old.add(Calendar.DAY_OF_MONTH, 1);
            Calendar calendar_now = Calendar.getInstance();
            calendar_now.setTime(nowDate);
            if ((calendar_now.get(Calendar.YEAR) == calendar_old.get(Calendar.YEAR))
                    && (calendar_now.get(Calendar.MONTH) == calendar_old.get(Calendar.MONTH))
                    && (calendar_now.get(Calendar.DAY_OF_MONTH) == calendar_old.get(Calendar.DAY_OF_MONTH))) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //获取年份
    private static int getNowYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    private static int getYearFromTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    //获取月
    private static int getNowMonth() {
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    private static int getMonthFromTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    //获取天
    private static int getNowDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    private static int getDayFromTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    //获取时
    private static int getNowHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    private static int getHourFromTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    //获取分
    private static int getNowMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    private static int getMinuteFromTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    //获取秒
    private static int getNowSecond() {
        return Calendar.getInstance().get(Calendar.SECOND);
    }

    private static int getSecondFromTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
   /* public Date getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }*/

    //检查是否有网络链接
    public static boolean IsNeiWork(Context context) {
        try {

//            Context context = activity.getApplicationContext();
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connectivityManager == null) {
                return false;
            } else {
                NetworkInfo[] networkInfo = connectivityManager
                        .getAllNetworkInfo();
                if (networkInfo != null && networkInfo.length > 0) {
                    for (int i = 0; i < networkInfo.length; i++) {
                        if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                            /*loge("---NET-success---",
                                    "=====" + networkInfo[i].getTypeName());*/
                            return true;
                        }
                    }
                }
            }
            return false;

        } catch (Exception e) {
//            Log.e("---NET-error---", e.getMessage());
            return false;
        }
    }

    public static void toOpenNetSetting(final Context context) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("网络异常,现在去设置网络？")
                    .setView(null)
                    .setPositiveButton("立即设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = null;
                            //判断手机系统的版本  即API大于10 就是3.0或以上版本
                            if (android.os.Build.VERSION.SDK_INT > 10) {
                                intent = new Intent(Settings.ACTION_SETTINGS);
                            } else {
                                intent = new Intent();
                                ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
                                intent.setComponent(component);
                                intent.setAction("android.intent.action.VIEW");
                            }
                            intent.putExtra("to_setting", true);
                            context.startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog dialog = builder.create();
            Window dialogWindow = dialog.getWindow();
            dialogWindow.setGravity(Gravity.CENTER);
            dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static void showPermissionDialog(final Activity mActivity, String title) {
        HelperFromPermission.showSimpleDialogForPermission(mActivity, "权限申请", "请前往【应用程序设置】-【权限管理】，开启【" + title + "】，以正常使用应用程序！",
                "立即设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface anInterface, int i) {
                        anInterface.dismiss();
                        //打开应用程序权限管理
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", mActivity.getPackageName(), null);
                        intent.setData(uri);

                        // Start for result
                        //noinspection NewApi The Builder constructor prevents this
                        mActivity.startActivityForResult(intent, 10);
                    }
                }, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface anInterface, int i) {
                        anInterface.dismiss();
                    }
                });
    }

    /**
     * 检查权限
     */
    public static boolean checkPermission(Context context, String pers) {
        return (ContextCompat.checkSelfPermission(context, pers) ==
                PackageManager.PERMISSION_GRANTED);
    }

    public static void showSimpleDialog(Context context, String title,
                                        String msg, String conform,
                                        DialogInterface.OnClickListener conformListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton(conform, conformListener);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        dialog.show();

    }


    public static void showSimpleDialog(Context context, String title,
                                        String msg, String conform,
                                        DialogInterface.OnClickListener conformListener,
                                        String cancle,
                                        DialogInterface.OnClickListener cancleListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton(conform, conformListener)
                .setNegativeButton(cancle, cancleListener);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        dialog.show();

    }


    public static void showSimpleDialog(Context context, String title,
                                        String msg, String conform,
                                        DialogInterface.OnClickListener conformListener, int color_conform,
                                        String cancle,
                                        DialogInterface.OnClickListener cancleListener, int color_cancle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton(conform, conformListener)
                .setNegativeButton(cancle, cancleListener);
        AlertDialog dialog = builder.create();
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        dialog.show();
        if (color_conform != 0) {
            dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(color_conform);
        }
        if (color_cancle != 0) {
            dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(color_cancle);
        }
    }

    public static void showSimpleDialog(Context context, String title,
                                        String msg, String conform,
                                        DialogInterface.OnClickListener conformListener, int color_conform, int gb_color_conform,
                                        String cancle,
                                        DialogInterface.OnClickListener cancleListener, int color_cancle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton(conform, conformListener)
                .setNegativeButton(cancle, cancleListener);
        AlertDialog dialog = builder.create();
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        dialog.show();
        if (color_conform != 0) {
            dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(color_conform);
            dialog.getButton(dialog.BUTTON_POSITIVE).setBackgroundColor(gb_color_conform);
        }
        if (color_cancle != 0) {
            dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(color_cancle);
        }
    }

    public static void showSimpleDialog(Context context, String title, String conform,
                                        DialogInterface.OnClickListener conformListener,
                                        String cancle,
                                        DialogInterface.OnClickListener cancleListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setView(null)
                .setPositiveButton(conform, conformListener)
                .setNegativeButton(cancle, cancleListener);
        AlertDialog dialog = builder.create();
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        dialog.show();
        dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#808080"));
        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#3881c5"));
    }

    /***
     * 权限提醒 dialog
     * @param context
     * @param title
     * @param msg
     * @param conform
     * @param conformListener
     * @param cancle
     * @param cancleListener
     */
    public static void showSimpleDialogForPermission(Context context, String title,
                                                     String msg, String conform,
                                                     DialogInterface.OnClickListener conformListener,
                                                     String cancle,
                                                     DialogInterface.OnClickListener cancleListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_pers_gps, null);
        TextView txt = (TextView) view.findViewById(R.id.lpg_txt_msg);
        txt.setText(Html.fromHtml(msg));
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setView(view)
                .setPositiveButton(conform, conformListener)
                .setNegativeButton(cancle, cancleListener)
                .setCancelable(false);
        AlertDialog dialog = builder.create();
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        dialog.show();
        dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#808080"));
        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#3881c5"));
    }

}

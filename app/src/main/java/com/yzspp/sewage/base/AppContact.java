package com.yzspp.sewage.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.List;

/**
 * 常量类
 */
public class AppContact {
    public static final float HOTSPOT_PRICE = 0.05f;
    public static final float HOTSPOT_FIXED_PRICE = 0.005f;

    public static boolean hasAnyMarketInstalled(Context context) {
        Intent intent =new Intent();
        intent.setData(Uri.parse("market://details?id=android.browser"));

        List list = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return 0!= list.size();
    }

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

}

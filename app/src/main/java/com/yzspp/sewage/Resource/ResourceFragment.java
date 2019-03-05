package com.yzspp.sewage.Resource;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yzspp.sewage.Bump.WetPointActivity;
import com.yzspp.sewage.R;
import com.yzspp.sewage.base.BaseFragment;
import com.yzspp.sewage.view.ResourceItem;
import com.yzspp.sewage.view.ResourceView;

/**
 * 一句话功能描述
 * 功能详细描述
 */
public class ResourceFragment extends BaseFragment {

    private ResourceItem ridot;
    private ResourceItem riwarn;
    private ResourceView rvcontact;
    private ResourceView rvemergency;
    private ResourceView rvpublicreport;
    private ResourceView rvtravel;
    private ResourceView rvnote;
    private ResourceView rvgift;

    public static ResourceFragment newInstance() {

        Bundle args = new Bundle();

        ResourceFragment fragment = new ResourceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmet_resource, container, false);
        return view;
    }

    @Override
    protected void initView(View view) {
        initToolbar((Toolbar) view.findViewById(R.id.toolbar), getString(R.string.resource), false);
        this.rvgift = (ResourceView) view.findViewById(R.id.rv_gift);
        this.rvnote = (ResourceView) view.findViewById(R.id.rv_note);
        this.rvtravel = (ResourceView) view.findViewById(R.id.rv_travel);
        this.rvpublicreport = (ResourceView) view.findViewById(R.id.rv_public_report);
        this.rvemergency = (ResourceView) view.findViewById(R.id.rv_emergency);
        this.rvcontact = (ResourceView) view.findViewById(R.id.rv_contact);
        this.riwarn = (ResourceItem) view.findViewById(R.id.ri_warn);
        this.ridot = (ResourceItem) view.findViewById(R.id.ri_dot);
    }

    @Override
    protected void setListener() {
        ridot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WetPointActivity.start(getActivity());
            }
        });

        riwarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testNotification();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {

    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    private void testNotification() {
        NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity());
        mBuilder.setContentTitle("扬州火车站渍水情况严重")//设置通知栏标题
                .setContentText(getString(R.string.test_notification))
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                .setTicker("预警信息！\n武昌火车站渍水情况严重") //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setOngoing(false)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.mipmap.ic_launcher);

        Notification notify = mBuilder.build();
        if (mNotificationManager != null) {
            mNotificationManager.notify(0,notify);
        }
    }
}

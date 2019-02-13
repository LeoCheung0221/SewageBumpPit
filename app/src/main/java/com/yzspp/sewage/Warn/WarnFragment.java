package com.yzspp.sewage.Warn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yzspp.sewage.R;
import com.yzspp.sewage.Warn.Chart.ChartActivity;
import com.yzspp.sewage.base.BaseFragment;
import com.yzspp.sewage.bean.DeviceInfo;
import com.yzspp.sewage.net.RequestHelper;
import com.yzspp.sewage.net.RequestListener;

import java.util.ArrayList;
import java.util.List;

import frame.tool.MyToast;
import frame.tool.SolidRVBaseAdapter;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * 一句话功能描述
 * 功能详细描述
 */
public class WarnFragment extends BaseFragment {

    private Toolbar mToolbar;
    private RecyclerView mRvDevice;
    private List<DeviceInfo> mDeviceInfoList = new ArrayList<>();
    private SolidRVBaseAdapter mAdapter;
    private static final String TAG = "WarnFragment";
    private FloatingActionButton mFloatingActionButton;

    public static WarnFragment newInstance() {

        Bundle args = new Bundle();

        WarnFragment fragment = new WarnFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_warn, container, false);
        return view;
    }

    @Override
    protected void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        initToolbar(mToolbar, getString(R.string.warn), false);
        mRvDevice = (RecyclerView) view.findViewById(R.id.rv_device);
        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab_map);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
//        RequestHelper.getDevices(new RequestListener() {
//            @Override
//            public void onResponce(String res) {
//                mAdapter.clearAllItems();
//                mAdapter.addItems(RequestHelper.stringToArray(res, DeviceInfo[].class));
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                MyToast.error(getActivity(), R.string.load_error);
//            }
//        });
        mAdapter = new SolidRVBaseAdapter<DeviceInfo>(getActivity(), mDeviceInfoList) {
            @Override
            protected void onBindDataToView(SolidCommonViewHolder holder, DeviceInfo bean) {
                holder.setText(R.id.tv_name, bean.getName());
                holder.setText(R.id.tv_location, bean.getPosition());
                holder.setText(R.id.tv_description, bean.getDescription());
                ImageView ivWarn = holder.getView(R.id.iv_warn);
                ivWarn.getDrawable().setLevel(bean.getDangerLevel() * 10);
            }

            @Override
            public int getItemLayoutID(int viewType) {
                return R.layout.item_device;
            }

            @Override
            protected void onItemClick(int position, DeviceInfo bean) {
                super.onItemClick(position, bean);
                Intent intent = new Intent(mContext, ChartActivity.class);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };
        mockData();

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceMapActivity.start(getActivity());
            }
        });

    }

    private void mockData() {
        String res = "[{\n" +
                "\t\t\"name\": \"设备1\",\n" +
                "\t\t\"danger_level\": 1,\n" +
                "\t\t\"position\": \"江苏省扬州市扬中区1\",\n" +
                "\t\t\"longitude\": 30.4013,\n" +
                "\t\t\"latitude\": 114.5031,\n" +
                "\t\t\"description\": \"111123adawdaw\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\": \"设备2\",\n" +
                "\t\t\"danger_level\": 2,\n" +
                "\t\t\"position\": \"江苏省扬州市扬中区2\",\n" +
                "\t\t\"longitude\": 31.4013,\n" +
                "\t\t\"latitude\": 115.5031,\n" +
                "\t\t\"description\": \"fdafasfsafa\"\n" +
                "\t}\n" +
                "]";
        mAdapter.clearAllItems();
        mAdapter.addItems(RequestHelper.stringToArray(res, DeviceInfo[].class));
    }

    @Override
    protected void setData() {
        mRvDevice.setAdapter(mAdapter);
        mRvDevice.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_warn, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_map) {
            DeviceMapActivity.start(getActivity());
        }
        return super.onOptionsItemSelected(item);
    }
}

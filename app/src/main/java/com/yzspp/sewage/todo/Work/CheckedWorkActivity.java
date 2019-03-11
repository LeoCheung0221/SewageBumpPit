package com.yzspp.sewage.todo.Work;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.yzspp.sewage.R;
import com.yzspp.sewage.base.BaseActivity;
import com.yzspp.sewage.bean.UploadInfo;
import com.yzspp.sewage.net.RequestHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import frame.tool.SolidRVBaseAdapter;

public class CheckedWorkActivity extends BaseActivity {

    private RecyclerView rvwork;
    private List<UploadInfo> mUploadInfoList = new ArrayList<>();
    private SolidRVBaseAdapter mAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, CheckedWorkActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_checked_work;
    }

    @Override
    protected void initView() {
        initeActionbar(R.string.checked_work, true);
        this.rvwork = (RecyclerView) findViewById(R.id.rv_work);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        mAdapter = new SolidRVBaseAdapter<UploadInfo>(this, mUploadInfoList) {
            @Override
            protected void onBindDataToView(SolidCommonViewHolder holder, UploadInfo bean) {
                holder.setImageFromInternet(R.id.iv_img, bean.getUploadResource());
                holder.setText(R.id.tv_name, bean.getUploadName());
                holder.setText(R.id.tv_location, bean.getUploadAddress());
                holder.setText(R.id.tv_time, new SimpleDateFormat("MM-dd HH:mm").format(new Date(bean.getUploadTime())));
                if (bean.getApprovalStatus() == 1) {
                    holder.setText(R.id.tv_state, getString(R.string.accepted));
                    holder.getView(R.id.tv_state).setBackgroundResource(R.drawable.bg_state_green);
                } else if (bean.getApprovalStatus() == 2) {
                    holder.setText(R.id.tv_state, getString(R.string.denied));
                    holder.getView(R.id.tv_state).setBackgroundResource(R.drawable.bg_state_red);
                }

            }

            @Override
            public int getItemLayoutID(int viewType) {
                return R.layout.item_work;
            }

            @Override
            protected void onItemClick(int position, UploadInfo bean) {
                super.onItemClick(position, bean);
                CheckDetailsActivity.start(CheckedWorkActivity.this, bean);
            }
        };
        loadWorks();
    }

    @Override
    protected void setData() {
        rvwork.setAdapter(mAdapter);
        rvwork.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    private void loadWorks() {
        loading();
        mockData();
//        RequestHelper.getUploadInfos(new RequestListener() {
//            @Override
//            public void onResponce(String responce) {
//                mUploadInfoList.clear();
//                List<UploadInfo> uploadInfoList=RequestHelper.stringToArray(responce, UploadInfo[].class);
//                for (UploadInfo uploadInfo : uploadInfoList) {
//                    if (uploadInfo.getApprovalStatus()!=0) {
//                        mUploadInfoList.add(uploadInfo);
//                    }
//                }
//                mAdapter.notifyDataSetChanged();
//                if (mAdapter.getItemCount()>0) {
//                    loadComplete();
//                }else {
//                    showEmpty();
//                }
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                loadFail();
//            }
//        });
    }

    private void mockData() {
        String responce = "[{\n" +
                "\t\"id\": 76,\n" +
                "\t\"upload_name\": \"扬州西路\",\n" +
                "\t\"upload_type\": \"公众\",\n" +
                "\t\"upload_resource\": \"https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1549938533&di=f77c8dc91053424e9767bf37e785e90a&src=http://www.lnwater.gov.cn/zxpd/dfss/fs/201704/W020170414316310554199.jpg\",\n" +
                "\t\"longitude\": 0,\n" +
                "\t\"latitude\": 0,\n" +
                "\t\"upload_address\": \"地点显示\",\n" +
                "\t\"upload_time\": 1507796319770,\n" +
                "\t\"upload_description\": \"test\",\n" +
                "\t\"approval_status\": 2\n" +
                "}]";
        mUploadInfoList.clear();
        List<UploadInfo> uploadInfoList = RequestHelper.stringToArray(responce, UploadInfo[].class);
        for (UploadInfo uploadInfo : uploadInfoList) {
            if (uploadInfo.getApprovalStatus() != 0) {
                mUploadInfoList.add(uploadInfo);
            }
        }
        mAdapter.notifyDataSetChanged();
        if (mAdapter.getItemCount() > 0) {
            loadComplete();
        } else {
            showEmpty();
        }
    }

    @Override
    protected void onRetryClick() {
        super.onRetryClick();
        loadFail();
    }

    @Override
    protected String[] getNeedPermissions() {
        return new String[0];
    }
}

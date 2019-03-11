package com.yzspp.sewage.todo.Work;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yzspp.sewage.R;
import com.yzspp.sewage.base.BaseFragment;
import com.yzspp.sewage.bean.UploadInfo;
import com.yzspp.sewage.net.RequestHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import frame.tool.SolidRVBaseAdapter;

/**
 * 一句话功能描述
 * 功能详细描述
 */
public class WorkFragment extends BaseFragment {

    private LinearLayout llcheck;
    private RecyclerView rvwork;
    private Toolbar mToolbar;
    private List<UploadInfo> mUploadInfoList = new ArrayList<>();
    private SolidRVBaseAdapter mAdapter;

    public static WorkFragment newInstance() {

        Bundle args = new Bundle();

        WorkFragment fragment = new WorkFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work, container, false);
        return view;
    }

    @Override
    protected void initView(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        initToolbar(mToolbar, getString(R.string.work), false);
        this.rvwork = (RecyclerView) view.findViewById(R.id.rv_work);
        this.llcheck = (LinearLayout) view.findViewById(R.id.ll_check);
    }

    @Override
    protected void setListener() {
        llcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckedWorkActivity.start(getActivity());
            }
        });
    }

    @Override
    protected void initData() {
        mAdapter = new SolidRVBaseAdapter<UploadInfo>(getActivity(), mUploadInfoList) {
            @Override
            protected void onBindDataToView(SolidCommonViewHolder holder, UploadInfo bean) {
                holder.setImageFromInternet(R.id.iv_img, bean.getUploadResource());
                holder.setText(R.id.tv_name, bean.getUploadName());
                holder.setText(R.id.tv_location, bean.getUploadAddress());
                holder.setText(R.id.tv_time, new SimpleDateFormat("MM-dd HH:mm").format(new Date(bean.getUploadTime())));
                holder.setText(R.id.tv_state, getString(R.string.uncheck));
                holder.getView(R.id.tv_state).setBackgroundResource(R.drawable.bg_state_blue);
            }

            @Override
            public int getItemLayoutID(int viewType) {
                return R.layout.item_work;
            }

            @Override
            protected void onItemClick(int position, UploadInfo bean) {
                super.onItemClick(position, bean);
                CheckDetailsActivity.start(getActivity(), bean);
            }
        };
        loadWorks();
    }

    @Override
    public void onStart() {
        super.onStart();
        loadWorks();
    }

    @Override
    protected void setData() {
        rvwork.setAdapter(mAdapter);
        rvwork.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    private void loadWorks() {
        mockData();
//        RequestHelper.getUploadInfos(new RequestListener() {
//            @Override
//            public void onResponce(String responce) {
//                mUploadInfoList.clear();
//                List<UploadInfo> uploadInfoList=RequestHelper.stringToArray(responce, UploadInfo[].class);
//                for (UploadInfo uploadInfo : uploadInfoList) {
//                    if (uploadInfo.getApprovalStatus()==0) {
//                        mUploadInfoList.add(uploadInfo);
//                    }
//                }
//                mAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                MyToast.error(getActivity(), R.string.load_error);
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
            if (uploadInfo.getApprovalStatus() == 0) {
                mUploadInfoList.add(uploadInfo);
            }
        }
        mAdapter.notifyDataSetChanged();
    }
}

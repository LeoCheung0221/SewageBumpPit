package com.yzspp.sewage.Bump;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.yzspp.sewage.R;
import com.yzspp.sewage.Work.CheckDetailsActivity;
import com.yzspp.sewage.base.BaseFragment;
import com.yzspp.sewage.bean.UploadInfo;
import com.yzspp.sewage.net.RequestHelper;
import com.yzspp.sewage.view.DiscoverView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import frame.tool.SolidRVBaseAdapter;

public class DiscoverFragment extends BaseFragment {

    private SliderLayout slider;
    private DiscoverView dvupload;
    private DiscoverView dvmap;
    private DiscoverView dvweather;
    private DiscoverView dvdot;
    private RecyclerView mRecyclerView;
    private List<UploadInfo> mUploadInfoList = new ArrayList<>();
    private SolidRVBaseAdapter mAdapter;

    public static DiscoverFragment newInstance() {

        Bundle args = new Bundle();

        DiscoverFragment fragment = new DiscoverFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        return view;
    }

    @Override
    protected void initView(View view) {
        initToolbar((Toolbar) view.findViewById(R.id.toolbar), getString(R.string.discover), false);
        this.slider = (SliderLayout) view.findViewById(R.id.slider);
        initSlider();
        this.dvdot = (DiscoverView) view.findViewById(R.id.dv_dot);
        this.dvweather = (DiscoverView) view.findViewById(R.id.dv_weather);
        this.dvmap = (DiscoverView) view.findViewById(R.id.dv_map);
        this.dvupload = (DiscoverView) view.findViewById(R.id.dv_upload);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_wet_point);
    }

    private void initSlider() {
        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("道路积水高，车辆受阻", R.drawable.test_my_work_one);
        file_maps.put("转移受灾群众", R.drawable.test_my_work_two);
        file_maps.put("强降雨引起淹水", R.drawable.test_my_work_three);
        file_maps.put("群众转移受灾物资", R.drawable.test_my_work_four);
        file_maps.put("救灾人员忙碌转移人员", R.drawable.test_my_work_five);


        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            slider.addSlider(textSliderView);
        }
        slider.setPresetTransformer(SliderLayout.Transformer.Default);
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slider.setCustomAnimation(new DescriptionAnimation());
        slider.setDuration(4000);
    }

    @Override
    protected void setListener() {
        dvupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReportSituationActivity.start(getActivity());
            }
        });

        dvmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapRepairActivity.start(getActivity());
            }
        });


        dvweather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeatherActivity.start(getActivity());
            }
        });

        dvdot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WetPointActivity.start(getActivity());
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
                holder.setText(R.id.tv_other, bean.getUploadDescription());
            }

            @Override
            public int getItemLayoutID(int viewType) {
                return R.layout.item_wet_point;
            }

            @Override
            protected void onItemClick(int position, UploadInfo bean) {
                super.onItemClick(position, bean);
                CheckDetailsActivity.start(getActivity(), bean);
            }
        };
        loadWetPoints();
    }

    @Override
    protected void setData() {
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    private void loadWetPoints() {
        mockData();
//        RequestHelper.getUploadInfos(new RequestListener() {
//            @Override
//            public void onResponce(String responce) {
//                mUploadInfoList.clear();
//                List<UploadInfo> uploadInfoList= RequestHelper.stringToArray(responce, UploadInfo[].class);
//                for (UploadInfo uploadInfo : uploadInfoList) {
//                    if (uploadInfo.getApprovalStatus()==1) {
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
            if (uploadInfo.getApprovalStatus() == 1) {
                mUploadInfoList.add(uploadInfo);
            }
        }
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onStart() {
        super.onStart();
        slider.startAutoCycle();
    }

    @Override
    public void onStop() {
        super.onStop();
        slider.stopAutoCycle();
    }


}

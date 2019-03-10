package com.yzspp.sewage.Feature;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.yzspp.sewage.Bump.MapRepairActivity;
import com.yzspp.sewage.Bump.WetPointActivity;
import com.yzspp.sewage.R;
import com.yzspp.sewage.Work.CheckDetailsActivity;
import com.yzspp.sewage.base.BaseActivity;
import com.yzspp.sewage.bean.UploadInfo;
import com.yzspp.sewage.widget.DiscoverView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import frame.tool.SolidRVBaseAdapter;

public class RainFallLevelActivity extends BaseActivity {

    private SliderLayout slider;
    private DiscoverView dvmap;
    private DiscoverView dvweather;
    private DiscoverView dvdot;
    private DiscoverView dvdotyear;
    private DiscoverView dvsearchdata;
    private RecyclerView mRecyclerView;
    private List<UploadInfo> mUploadInfoList = new ArrayList<>();
    private SolidRVBaseAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rain_fall_level;
    }

    @Override
    protected void initView() {
        initToolbar("重点水位/雨量信息", true);
        this.slider = (SliderLayout) findViewById(R.id.slider);
        initSlider();
        this.dvdot = (DiscoverView) findViewById(R.id.dv_dot);
        this.dvdotyear = (DiscoverView) findViewById(R.id.dv_dot_year);
        this.dvweather = (DiscoverView) findViewById(R.id.dv_weather);
        this.dvmap = (DiscoverView) findViewById(R.id.dv_map);
        this.dvsearchdata = (DiscoverView) findViewById(R.id.dv_search_data);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_wet_point);
    }

    private void initSlider() {
        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("道路积水高，车辆受阻", R.drawable.test_my_work_one);
        file_maps.put("转移受灾群众", R.drawable.test_my_work_two);
        file_maps.put("强降雨引起淹水", R.drawable.test_my_work_three);
        file_maps.put("群众转移受灾物资", R.drawable.test_my_work_four);
        file_maps.put("救灾人员忙碌转移人员", R.drawable.test_my_work_five);


        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
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
        dvmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapRepairActivity.start(RainFallLevelActivity.this);
            }
        });


        dvweather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                WeatherActivity.start(BumpManagerActivity.this);
                WetPointActivity.start(RainFallLevelActivity.this);
            }
        });

        dvdot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WetPointActivity.start(RainFallLevelActivity.this);
            }
        });

        dvdotyear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WetPointActivity.start(RainFallLevelActivity.this);
            }
        });

        dvsearchdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WetPointActivity.start(RainFallLevelActivity.this);
            }
        });
    }

    @Override
    protected void initData() {
        mAdapter = new SolidRVBaseAdapter<UploadInfo>(RainFallLevelActivity.this, mUploadInfoList) {
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
                CheckDetailsActivity.start(RainFallLevelActivity.this, bean);
            }
        };
        loadWetPoints();
    }

    @Override
    protected void setData() {
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(RainFallLevelActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }

    private void loadWetPoints() {
//        mockData();
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
//                MyToast.error(BumpManagerActivity.this, R.string.load_error);
//            }
//        });
    }

    private void mockData() {
        mUploadInfoList.clear();
        List<UploadInfo> uploadInfoList = new ArrayList<>();
        UploadInfo uplInfoBean1 = new UploadInfo();
        uplInfoBean1.setUploadName("泵站实时监测");
        uplInfoBean1.setUpload_resource("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1549938533&di=f77c8dc91053424e9767bf37e785e90a&src=http://www.lnwater.gov.cn/zxpd/dfss/fs/201704/W020170414316310554199.jpg");
        uploadInfoList.add(uplInfoBean1);

        UploadInfo uplInfoBean2 = new UploadInfo();
        uplInfoBean2.setUploadName("每日泵站信息");
        uplInfoBean2.setUpload_resource("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1549938533&di=f77c8dc91053424e9767bf37e785e90a&src=http://www.lnwater.gov.cn/zxpd/dfss/fs/201704/W020170414316310554199.jpg");
        uploadInfoList.add(uplInfoBean2);

        for (UploadInfo uploadInfo : uploadInfoList) {
            mUploadInfoList.add(uploadInfo);
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

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    @Override
    protected String[] getNeedPermissions() {
        return new String[0];
    }
}

package com.yzspp.sewage.Feature;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.yzspp.sewage.R;
import com.yzspp.sewage.base.BaseActivity;
import com.yzspp.sewage.bean.ReportInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import frame.tool.MyToast;
import frame.tool.SolidRVBaseAdapter;

public class WaterLeachingReportActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvStartPick;
    private TextView tvEndPick;
    private RecyclerView mRecyclerView;

    private String dateStr, mouth1, day1;
    final Calendar calender = Calendar.getInstance();

    private List<ReportInfo> mUploadInfoList = new ArrayList<>();
    private SolidRVBaseAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_water_leaching_report;
    }

    @Override
    protected void initView() {
        initToolbar("报警报告", true, false);
        mRecyclerView = findViewById(R.id.rv_water_leaching_view);
        tvStartPick = findViewById(R.id.tvStartPick);
        tvEndPick = findViewById(R.id.tvEndPick);
    }

    @Override
    protected void setListener() {
        tvStartPick.setOnClickListener(this);
        tvEndPick.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mAdapter = new SolidRVBaseAdapter<ReportInfo>(WaterLeachingReportActivity.this, mUploadInfoList) {
            @Override
            protected void onBindDataToView(SolidCommonViewHolder holder, ReportInfo bean) {
                holder.setText(R.id.tv_name, "报警报告概述");
                holder.setText(R.id.tv_start_name, "降雨开始时间");
                holder.setText(R.id.tv_end_name, "降雨结束时间");
                holder.setText(R.id.tv_report_name, "报告提交时间");

                holder.setText(R.id.tv_water_desc, bean.getWater_desc());
                holder.setText(R.id.tv_start_time, bean.getWater_start_time());
                holder.setText(R.id.tv_end_time, bean.getWater_end_time());
                holder.setText(R.id.tv_report_time, bean.getReport_submit_time());

                holder.getView(R.id.btn_go).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyToast.success(WaterLeachingReportActivity.this, "查看报告");
                    }
                });
                holder.getView(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyToast.success(WaterLeachingReportActivity.this, "编辑报告");
                    }
                });
            }

            @Override
            public int getItemLayoutID(int viewType) {
                return R.layout.item_water_report_list;
            }

            @Override
            protected void onItemClick(int position, ReportInfo bean) {
                super.onItemClick(position, bean);
                MyToast.success(WaterLeachingReportActivity.this, "跳转报告详情");
            }
        };
        loadInfo();
    }

    private void loadInfo() {
        mUploadInfoList.clear();
        List<ReportInfo> uploadInfoList = new ArrayList<>();
        ReportInfo uplInfoBean1 = new ReportInfo();
        uplInfoBean1.setWater_desc("XXXXXXX区水位10米，天沙河水位10米，西江水位12米");
        uplInfoBean1.setWater_start_time("2019-02-12 16:37");
        uplInfoBean1.setWater_end_time("2019-02-12 20:37");
        uplInfoBean1.setReport_submit_time("2019-02-13 00:37");
        uploadInfoList.add(uplInfoBean1);

        ReportInfo uplInfoBean2 = new ReportInfo();
        uplInfoBean2.setWater_desc("BB区水位5米，CC河水位10米，DD水位2米");
        uplInfoBean2.setWater_start_time("2019-02-11 14:37");
        uplInfoBean2.setWater_end_time("2019-02-11 16:37");
        uplInfoBean2.setReport_submit_time("2019-02-12 00:37");
        uploadInfoList.add(uplInfoBean2);

        ReportInfo uplInfoBean3 = new ReportInfo();
        uplInfoBean3.setWater_desc("QQQ区水位5米，PPP河水位10米，ASDDAS水位2米");
        uplInfoBean3.setWater_start_time("2019-02-10 14:37");
        uplInfoBean3.setWater_end_time("2019-02-10 17:37");
        uplInfoBean3.setReport_submit_time("2019-02-11 22:37");
        uploadInfoList.add(uplInfoBean3);

        for (ReportInfo uploadInfo : uploadInfoList) {
            mUploadInfoList.add(uploadInfo);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void setData() {
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(WaterLeachingReportActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    @Override
    protected String[] getNeedPermissions() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvStartPick:
                selectStartTimePick();
                break;
            case R.id.tvEndPick:
                selectEndTimePick();
                break;
        }
    }

    private void selectEndTimePick() {
        DatePickerDialog dialog = new
                DatePickerDialog(WaterLeachingReportActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int
                    dayOfMonth) {

                if (monthOfYear <= 9) {
                    mouth1 = "0" + (monthOfYear + 1);
                } else {
                    mouth1 = String.valueOf(monthOfYear + 1);
                }
                if (dayOfMonth <= 9) {
                    day1 = "0" + dayOfMonth;
                } else {
                    day1 = String.valueOf(dayOfMonth);
                }
                dateStr = String.valueOf(year) + "-" + mouth1 + "-" + day1;
                tvEndPick.setText(dateStr);
            }
        }, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void selectStartTimePick() {
        DatePickerDialog dialog = new
                DatePickerDialog(WaterLeachingReportActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int
                    dayOfMonth) {

                if (monthOfYear <= 9) {
                    mouth1 = "0" + (monthOfYear + 1);
                } else {
                    mouth1 = String.valueOf(monthOfYear + 1);
                }
                if (dayOfMonth <= 9) {
                    day1 = "0" + dayOfMonth;
                } else {
                    day1 = String.valueOf(dayOfMonth);
                }
                dateStr = String.valueOf(year) + "-" + mouth1 + "-" + day1;
                tvStartPick.setText(dateStr);
            }
        }, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }
}

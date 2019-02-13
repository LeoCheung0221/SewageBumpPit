package com.yzspp.sewage.bean;

import java.io.Serializable;

/**
 *
 */
public class ReportInfo implements Serializable {

    private String water_desc; //水浸报告描述
    private String water_start_time; //降雨开始时间
    private String water_end_time; //降雨结束时间
    private String report_submit_time; //报告提交时间

    public String getWater_desc() {
        return water_desc;
    }

    public void setWater_desc(String water_desc) {
        this.water_desc = water_desc;
    }

    public String getWater_start_time() {
        return water_start_time;
    }

    public void setWater_start_time(String water_start_time) {
        this.water_start_time = water_start_time;
    }

    public String getWater_end_time() {
        return water_end_time;
    }

    public void setWater_end_time(String water_end_time) {
        this.water_end_time = water_end_time;
    }

    public String getReport_submit_time() {
        return report_submit_time;
    }

    public void setReport_submit_time(String report_submit_time) {
        this.report_submit_time = report_submit_time;
    }
}

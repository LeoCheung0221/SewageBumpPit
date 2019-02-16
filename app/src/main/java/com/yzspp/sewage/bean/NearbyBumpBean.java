package com.yzspp.sewage.bean;


import frame.base.AbsJavaBean;

/**
 * author：leo on 2018/5/4 0004 09:22
 * email： leocheung4ever@gmail.com
 * description: 我附近泵站信息
 * what & why is modified:
 */
public class NearbyBumpBean extends AbsJavaBean {

    /**
     * sorts : null
     * ranges : null
     * id : 2
     * createTime : 1524069561000
     * modifyTime : 1524665445000
     * shareNum : PS20180419003921309329
     * userId : 19
     * carportId : 2
     * parkingTicketId : null
     * startTime : 2018-04-25 22:10:45
     * stopTime : 2018-04-19 01:39:00
     * price : 12.8
     * status : 0
     * carportMeid : 2
     * carportNum : 384-2201
     * communityId : 2
     * communityName : 方家花苑
     * communityType : 2
     * province : 浙江省
     * city : 杭州市
     * area : 拱墅区
     * addr : 萍水西街我也不知道多少号
     * longitude : 120.161331
     * latitude : 29.393634
     */

    private String sorts;
    private String ranges;
    private int id;
    private long createTime;
    private long modifyTime;
    private String shareNum;
    private int userId;
    private int carportId;
    private String parkingTicketId;
    private String startTime;
    private String stopTime;
    private double price;
    private int status;
    private String carportMeid;
    private String carportNum;
    private int communityId;
    private String communityName;
    private int communityType;
    private String province;
    private String city;
    private String area;
    private String addr;
    private double longitude;
    private double latitude;

    public String getSorts() {
        return sorts;
    }

    public void setSorts(String sorts) {
        this.sorts = sorts;
    }

    public String getRanges() {
        return ranges;
    }

    public void setRanges(String ranges) {
        this.ranges = ranges;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getShareNum() {
        return shareNum;
    }

    public void setShareNum(String shareNum) {
        this.shareNum = shareNum;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCarportId() {
        return carportId;
    }

    public void setCarportId(int carportId) {
        this.carportId = carportId;
    }

    public String getParkingTicketId() {
        return parkingTicketId;
    }

    public void setParkingTicketId(String parkingTicketId) {
        this.parkingTicketId = parkingTicketId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCarportMeid() {
        return carportMeid;
    }

    public void setCarportMeid(String carportMeid) {
        this.carportMeid = carportMeid;
    }

    public String getCarportNum() {
        return carportNum;
    }

    public void setCarportNum(String carportNum) {
        this.carportNum = carportNum;
    }

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public int getCommunityType() {
        return communityType;
    }

    public void setCommunityType(int communityType) {
        this.communityType = communityType;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}

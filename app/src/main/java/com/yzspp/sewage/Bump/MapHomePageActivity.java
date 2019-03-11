package com.yzspp.sewage.Bump;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.overlay.DrivingRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.yzspp.sewage.R;
import com.yzspp.sewage.utils.HelperFromPermission;
import com.yzspp.sewage.utils.SSIntentTool;
import com.yzspp.sewage.bean.NearbyBumpBean;
import com.yzspp.sewage.bean.UploadInfo;
import com.yzspp.sewage.net.RequestHelper;
import com.yzspp.sewage.widget.My2dMapView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import frame.permission.CheckPermissionsActivity;
import frame.tool.MyToast;


public class MapHomePageActivity extends CheckPermissionsActivity implements LocationSource,
        AMapLocationListener, PoiSearch.OnPoiSearchListener, View.OnClickListener,
        AMap.OnMarkerClickListener, RouteSearch.OnRouteSearchListener {

    //高德地图
    private My2dMapView mapView;
    private AMapLocationClient mNavLocationClient;
    private AMap aNavMap;

    private LocationSource.OnLocationChangedListener mNavListener;
    private boolean isFirstNavLoc = true;

    //    private static final String SEARCH_KEYWORD = "停车场";
    private static final String SEARCH_KEYWORD = "";
    private static final String POI_SEARCH_TYPE = "";

    private PoiSearch.Query query;
    private int currentPage = 0;
    private PoiSearch poiSearch;
    private PoiResult poiResult;

    private Marker lastCheckedMarker;
    private ArrayList<BitmapDescriptor> lastCheckedBitmapDescriptorList;
    private LatLonPoint endLocation;
    private String endAddress;
    private RouteSearch.DriveRouteQuery driveQuery;
    private ArrayList<PoiItem> poiItems;
    private LatLngBounds.Builder boundBuilder;
    private ArrayList<BitmapDescriptor> bitmapDescriptorArrayList;
    private LatLng mLatLng;

    private List<Marker> mMarkerList = new ArrayList<>();
    private List<NearbyBumpBean> mNearbyParkingMineBeen = new ArrayList<>();
    private AMapLocation mAmapLocation;

    //控件
    private CardView ivSkip2CustomerService;
    private CardView ivSkip2MyLocation;
    private Spinner mSpinner;
    private Button mBtnDetails;
    private LinearLayout llBumpInfoView;

    private int nowPoint = 0;
    private boolean isClickBumpView = false;

    public static void start(Context context) {
        Intent starter = new Intent(context, MapHomePageActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_map);

        mSpinner = findViewById(R.id.sp_repair_map_point_choose);
        mBtnDetails = findViewById(R.id.btn_repair_map_details);
        llBumpInfoView = findViewById(R.id.llBumpInfoView);
        ivSkip2CustomerService = findViewById(R.id.ivSkip2CustomerService);
        ivSkip2MyLocation = findViewById(R.id.ivSkip2MyLocation);
        mapView = findViewById(R.id.map_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //检查是否拥有定位权限
            if (!HelperFromPermission.checkPermission(MapHomePageActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                String[] perms = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION};
                /***
                 * 没有授权，尝试获取权限。
                 * 1、第一次询问用户是否需要权限，弹出授权框。
                 * 2、以前被拒绝过，系统将不理会此程序的授权申请，弹出提示由用户自行处理。
                 */
                if (ActivityCompat.shouldShowRequestPermissionRationale(MapHomePageActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    ActivityCompat.requestPermissions(MapHomePageActivity.this, perms, 100);
                } else {
                    HelperFromPermission.showPermissionDialog(MapHomePageActivity.this, "定位权限");
                }
                return;
            }
        }
        //检查定位权限
        checkPermissions();
        mapView.onCreate(savedInstanceState);// 此方法必须重写

        //初始化控件
        initView();

        //从服务器获取点的信息并用sharedpreference存储到本地
        receivePointInfo();

        setMySpinner();
    }

    private void setMySpinner() {
        ArrayList<String> nameList = new ArrayList<>();
        nameList.add("江都城区污水泵站");
        nameList.add("邗江区杨庙镇污水泵站");
        nameList.add("经济开发区污水泵站");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, nameList);
        mSpinner.setAdapter(adapter);

        //设置spinner点击事件
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nowPoint = position;
                //使用arrayMarkers的定位功能
                ArrayList<MarkerOptions> arrayMarkers = new ArrayList<MarkerOptions>();
                ArrayList<Marker> markers = new ArrayList<Marker>();

//                LatLng mLatLng = new LatLng(
//                        mUploadInfoList.get(position).getLatitude(),
//                        mUploadInfoList.get(position).getLongitude());
//                arrayMarkers.add(new MarkerOptions()
//                        .title(mUploadInfoList.get(position).getUploadName())
//                        .icon(BitmapDescriptorFactory
//                                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
//                        .position(mLatLng));
//                markers = aMap.addMarkers(arrayMarkers, true);
//                markers.get(0).showInfoWindow();
//
//                //设置底部栏可见并且更新信息
////                setInfoTipVisibile();
//                refreshTipInfo(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initView() {
        initMapView();
        initInfo();
    }

    private void initInfo() {
        mBtnDetails.setOnClickListener(this);
        ivSkip2CustomerService.setOnClickListener(this);
        ivSkip2MyLocation.setOnClickListener(this);
    }

    private void initMapView() {
        if (aNavMap == null) {
            aNavMap = mapView.getMap();
        }
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        aNavMap.setMapType(AMap.MAP_TYPE_NORMAL);

        UiSettings settings = aNavMap.getUiSettings(); //设置显示定位按钮 并且可以点击
        aNavMap.setLocationSource(this); //设置监听 这里实现LocationSource接口
        settings.setMyLocationButtonEnabled(false); //是否显示定位按钮
        settings.setZoomControlsEnabled(false); //是否显示缩放按钮 此处设置不显示
        settings.setCompassEnabled(false); //显示指南针
        aNavMap.setMyLocationEnabled(true); //显示定位层 并且可以出发定位 默认是false
        aNavMap.setOnMarkerClickListener(this); //Marker点击事件

        //获得当前定位信息
        mNavLocationClient = new AMapLocationClient(this);
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        mNavLocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setInterval(2000);
        mNavLocationClient.setLocationOption(mLocationOption);
        mNavLocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(final AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude(); //获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy(); //获取精度信息
                amapLocation.getCityCode(); //获得城市编码
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间

                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstNavLoc) {
                    //去除蓝色透明范围圈
                    MyLocationStyle myLocationStyle = new MyLocationStyle();
                    myLocationStyle.radiusFillColor(android.R.color.transparent);
                    myLocationStyle.strokeColor(android.R.color.transparent);
                    myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.icon_parking_loc));
                    aNavMap.setMyLocationStyle(myLocationStyle);
//                    aNavMap.addMarker(getLocationMarkerOptions());

                    //设置缩放级别
                    aNavMap.moveCamera(CameraUpdateFactory.zoomTo(15));
                    //将地图移动到定位点
                    aNavMap.moveCamera(CameraUpdateFactory.changeLatLng(
                            new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude())));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mNavListener.onLocationChanged(amapLocation);
                    mLatLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getNearbyByLatLng(amapLocation, mLatLng);
                        }
                    });

                    isFirstNavLoc = false;
                }
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    private void getNearbyByLatLng(final AMapLocation amapLocation, LatLng latLng) {
//        ApiManage.get(ApiHost.getInstance().loadByDistance() + latLng.longitude + "/" + latLng.latitude + "/" + "10")
//                .tag(this)
//                .cacheKey("cacheKey")
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(String s, Call call, Response response) {
//                        if (response.code() == 200) {
//                            try {
//                                JSONObject json = new JSONObject(s);
//                                mNearbyParkingMineBeen = com.alibaba.fastjson.JSONObject.parseArray(String.valueOf(json.get("data")), NearbyBumpBean.class);
        if (mNearbyParkingMineBeen != null && mNearbyParkingMineBeen.size() > 0) {
            //根据指定经纬度 地图显示
            prepareSearchNearbyParking(amapLocation, mNearbyParkingMineBeen);
        } else {
            prepareSearchNearbyParking(amapLocation);
//                                    Toast.makeText(MapHomePageActivity.this, "附近暂未搜索到泵站点", Toast.LENGTH_LONG).show();
        }
//
//                                //开始POI搜索
////                                searchByPoi(amapLocation);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        } else {
//                            ToastUtils.show(response.message());
//                        }
//                    }
//                });
    }

    private void prepareSearchNearbyParking(AMapLocation amapLocation, List<NearbyBumpBean> parkingMineBeanList) {
        this.mAmapLocation = amapLocation;
        boundBuilder = new LatLngBounds.Builder();
        String iconName = "icon_poi_marker_parking";
        int iconId = getResources().getIdentifier(iconName, "drawable", this.getPackageName());
        for (int j = 0; j < parkingMineBeanList.size(); j++) {
            MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(iconId));
            markerOptions.position(new LatLng(parkingMineBeanList.get(j).getLatitude(), parkingMineBeanList.get(j).getLongitude()));
            markerOptions.title(parkingMineBeanList.get(j).getCity()).snippet(parkingMineBeanList.get(j).getCity() + "：" + parkingMineBeanList.get(j).getLatitude() + "，" + parkingMineBeanList.get(j).getLongitude());
            markerOptions.draggable(true);//设置Marker可拖动
            Marker marker = aNavMap.addMarker(markerOptions);
            marker.setObject(j);
            //为了POI填充整个地图区域
            boundBuilder.include(new LatLng(parkingMineBeanList.get(j).getLatitude(), parkingMineBeanList.get(j).getLongitude()));
            mMarkerList.add(marker);
        }
        LatLngBounds bounds = boundBuilder.build();
        // 移动地图，所有marker自适应显示。LatLngBounds与地图边缘10像素的填充区域
        aNavMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
    }

    private void prepareSearchNearbyParking(AMapLocation amapLocation) {
        this.mAmapLocation = amapLocation;
        boundBuilder = new LatLngBounds.Builder();
        String iconName = "icon_poi_marker_parking";
        int iconId = getResources().getIdentifier(iconName, "drawable", this.getPackageName());
        MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(iconId));
        markerOptions.position(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()));
        markerOptions.title(amapLocation.getCity()).snippet(amapLocation.getCity() + "：" + amapLocation.getLatitude() + "，" + amapLocation.getLongitude());
        markerOptions.draggable(true);//设置Marker可拖动
        Marker marker = aNavMap.addMarker(markerOptions);
        marker.setObject(0);
        //为了POI填充整个地图区域
        boundBuilder.include(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()));
        mMarkerList.add(marker);

        LatLngBounds bounds = boundBuilder.build();
        // 移动地图，所有marker自适应显示。LatLngBounds与地图边缘10像素的填充区域
        aNavMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        poiResult = null;
        if (aNavMap != null)
            aNavMap.clear();
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mNavListener = onLocationChangedListener;
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mNavListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_repair_map_details:
                if (isClickBumpView) {
                    isClickBumpView = false;
                    llBumpInfoView.setVisibility(View.VISIBLE);
                } else {
                    isClickBumpView = true;
                    llBumpInfoView.setVisibility(View.GONE);
                }
                break;
            case R.id.ivSkip2CustomerService:
//                startActivity(new Intent(MapHomePageActivity.this, CustomerServiceMineActivity.class));
                break;
            case R.id.ivSkip2MyLocation:
                location();
                break;
        }
    }

    public void location() {
        aNavMap.moveCamera(CameraUpdateFactory.changeLatLng(mLatLng));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for (int i = 0; i < mMarkerList.size(); i++) {
            if (marker.equals(mMarkerList.get(i))) {
                if (aNavMap != null) {
                    SSIntentTool.start(MapHomePageActivity.this, BumpOverviewActivity.class);
//                    final int finalI = i;
//                    new BottomDialog(MapHomePageActivity.this)
//                            .layout(BottomDialog.GRID)
//                            .orientation(BottomDialog.VERTICAL)
//                            .nav(new BottomDialog.OnSkip2NavigationListener() {
//                                @Override
//                                public void nav() {
////                                    NaviLatLng startNavi = new NaviLatLng(mAmapLocation.getLatitude(), mAmapLocation.getLongitude());
////                                    NaviLatLng endNavi = new NaviLatLng(mNearbyParkingMineBeen.get(finalI).getLatitude(), mNearbyParkingMineBeen.get(finalI).getLongitude());
////                                    startActivity(new Intent(MapHomePageActivity.this, Navigation2DActivity.class)
////                                            .putExtra("start_navi_point", startNavi)
////                                            .putExtra("end_navi_point", endNavi));
//                                }
//                            })
//                            .match(new BottomDialog.OnSkip2MatchListener() {
//                                @Override
//                                public void match() {
////                                    startActivity(new Intent(MapHomePageActivity.this, ParkingSpaceImmMatchingActivity.class).putExtra("destination_navi_info", mNearbyParkingMineBeen.get(finalI)));
//                                }
//                            })
////                            .setData(mAmapLocation, mNearbyParkingMineBeen.get(finalI))
//                            .show();
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        if (rCode == 1000) {
            if (result != null && result.getQuery() != null) {
                if (result.getQuery().equals(query)) { //是否是同一条
                    poiResult = result;
                    //取得搜索到的poiitem有多少页
                    int resultPages = poiResult.getPageCount();
                    //取得第一页的poiitem数据
                    poiItems = poiResult.getPois();
                    //当搜索不到poiitem数据时,会返回含有搜索关键字的城市信息
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();

                    if (poiItems != null && poiItems.size() > 0) {
                        boundBuilder = new LatLngBounds.Builder();
                        for (int j = 0; j < Math.min(poiItems.size(), 10); j++) {
                            String iconName = "icon_poi_marker_parking";
                            int iconId = getResources().getIdentifier(iconName, "drawable", this.getPackageName());
                            MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(iconId));
                            markerOptions.position(new LatLng(poiItems.get(j).getLatLonPoint().getLatitude(), poiItems.get(j).getLatLonPoint().getLongitude()));
                            Marker marker = aNavMap.addMarker(markerOptions);
                            marker.setObject(j + 1);
                            //为了POI填充整个地图区域
                            boundBuilder.include(new LatLng(poiItems.get(j).getLatLonPoint().getLatitude(), poiItems.get(j).getLatLonPoint().getLongitude()));
                            if (j == 0) {
                                lastCheckedMarker = marker;
                                lastCheckedBitmapDescriptorList = lastCheckedMarker.getIcons();
                                ArrayList<BitmapDescriptor> bitmapDescriptorArrayList = new ArrayList<>();
                                bitmapDescriptorArrayList.add(BitmapDescriptorFactory.fromResource(R.drawable.icon_poi_marker_parking));
                                marker.setIcons(bitmapDescriptorArrayList);
                                marker.setObject(1);
//                                tvParkingLot.setText("1. " + poiItems.get(0).getTitle());
//                                tvDestLocation.setText(poiItems.get(0).getSnippet());
                                endLocation = poiResult.getPois().get(0).getLatLonPoint();
                                endAddress = poiResult.getPois().get(0).getTitle();
                            }

                            mMarkerList.add(marker);
                        }
                        LatLngBounds bounds = boundBuilder.build();
                        // 移动地图，所有marker自适应显示。LatLngBounds与地图边缘10像素的填充区域
                        aNavMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
                    } else if (suggestionCities != null && suggestionCities.size() > 0) {
                        showSuggestCity(suggestionCities);
                    } else {
                        Toast.makeText(MapHomePageActivity.this, "附近暂无搜索结果", Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                Toast.makeText(MapHomePageActivity.this, "附近暂无搜索结果", Toast.LENGTH_LONG).show();
            }
        } else {
            MyToast.error(MapHomePageActivity.this, rCode + "");
        }
    }

    /**
     * 含有搜索关键字的城市信息列表展示
     */
    private void showSuggestCity(List<SuggestionCity> suggestionCities) {
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int rCode) {
        //解析result获取路径规划结果
        if (rCode == 1000) {
            if (result != null && result.getDriveQuery() != null) {
                if (result.getDriveQuery().equals(driveQuery)) { //是否是同一条
                    DrivePath drivePath = result.getPaths().get(0);
                    DrivingRouteOverlay routeOverlay =
                            new DrivingRouteOverlay(this, aNavMap,//第一个参数是context，2.是地图
                                    drivePath, result.getStartPos(),//3.驾车线路，4.出发位置
                                    result.getTargetPos(), null); //5.终点位置
                    routeOverlay.removeFromMap(); //去掉DrivingRouteOverlay上所有的Marker
                    routeOverlay.addToMap(); //添加驾车线路到地图
                    routeOverlay.zoomToSpan(); //移动镜头当前视角
                }
            }
        }
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

    //从数据库获取点的信息
    private void receivePointInfo() {
        mockData();
//        RequestHelper.getUploadInfos(new RequestListener() {
//            @Override
//            public void onResponce(String responce) {
//                List<UploadInfo> uploadInfoList=RequestHelper.stringToArray(responce, UploadInfo[].class);
//                for (UploadInfo uploadInfo : uploadInfoList) {
//                    if (uploadInfo.getApprovalStatus()==1) {
//                        mUploadInfoList.add(uploadInfo);
//                    }
//                }
//                //初始化控件
//                initView();
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                MyToast.error(MapHomePageActivity.this, R.string.load_error);
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
        List<UploadInfo> uploadInfoList = RequestHelper.stringToArray(responce, UploadInfo[].class);
        for (UploadInfo uploadInfo : uploadInfoList) {
            if (uploadInfo.getApprovalStatus() == 1) {
//                mUploadInfoList.add(uploadInfo);
            }
        }
    }

    @Override
    protected String[] getNeedPermissions() {
        return new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
        };
    }

}

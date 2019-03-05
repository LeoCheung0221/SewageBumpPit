package com.yzspp.sewage.Bump.frag;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.yzspp.sewage.Bump.MapRepairActivity;
import com.yzspp.sewage.R;
import com.yzspp.sewage.Tools.HelperFromPermission;
import com.yzspp.sewage.base.BaseFragment;
import com.yzspp.sewage.bean.NearbyBumpBean;
import com.yzspp.sewage.bean.UploadInfo;
import com.yzspp.sewage.net.RequestHelper;
import com.yzspp.sewage.view.My2dMapView;
import com.yzspp.sewage.view.bottomdialog.BottomDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import frame.tool.MyToast;

/**
 * 总览碎片
 */
public class OverViewFragment extends BaseFragment implements LocationSource,
        AMapLocationListener, PoiSearch.OnPoiSearchListener, AMap.OnMarkerClickListener, RouteSearch.OnRouteSearchListener {


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


    public OverViewFragment() {
    }

    public static OverViewFragment newInstance() {
        OverViewFragment fragment = new OverViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //检查是否拥有定位权限
            if (!HelperFromPermission.checkPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
                String[] perms = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION};
                /***
                 * 没有授权，尝试获取权限。
                 * 1、第一次询问用户是否需要权限，弹出授权框。
                 * 2、以前被拒绝过，系统将不理会此程序的授权申请，弹出提示由用户自行处理。
                 */
                if (ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(getActivity()), Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), perms, 100);
                } else {
                    HelperFromPermission.showPermissionDialog(getActivity(), "定位权限");
                }
                return;
            }
        }
        //检查定位权限
        checkPermissions();

        //从服务器获取点的信息并用sharedpreference存储到本地
        receivePointInfo();
    }

    @Override
    protected View initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_over_view, container, false);
        mapView = view.findViewById(R.id.map_main);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        return view;
    }

    @Override
    protected void initView(View view) {
        initMapView();
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
        mNavLocationClient = new AMapLocationClient(getContext());
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

                    Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
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
//                                    Toast.makeText(MapRepairActivity.this, "附近暂未搜索到泵站点", Toast.LENGTH_LONG).show();
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
        int iconId = getResources().getIdentifier(iconName, "drawable", Objects.requireNonNull(getContext()).getPackageName());
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
        int iconId = getResources().getIdentifier(iconName, "drawable", Objects.requireNonNull(getContext()).getPackageName());
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
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    @Override
    public void onDestroy() {
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

    public void location() {
        aNavMap.moveCamera(CameraUpdateFactory.changeLatLng(mLatLng));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for (int i = 0; i < mMarkerList.size(); i++) {
            if (marker.equals(mMarkerList.get(i))) {
                if (aNavMap != null) {
                    final int finalI = i;
                    new BottomDialog(getContext())
                            .layout(BottomDialog.GRID)
                            .orientation(BottomDialog.VERTICAL)
                            .nav(new BottomDialog.OnSkip2NavigationListener() {
                                @Override
                                public void nav() {
//                                    NaviLatLng startNavi = new NaviLatLng(mAmapLocation.getLatitude(), mAmapLocation.getLongitude());
//                                    NaviLatLng endNavi = new NaviLatLng(mNearbyParkingMineBeen.get(finalI).getLatitude(), mNearbyParkingMineBeen.get(finalI).getLongitude());
//                                    startActivity(new Intent(MapRepairActivity.this, Navigation2DActivity.class)
//                                            .putExtra("start_navi_point", startNavi)
//                                            .putExtra("end_navi_point", endNavi));
                                }
                            })
                            .show();
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
                            int iconId = getResources().getIdentifier(iconName, "drawable", Objects.requireNonNull(getContext()).getPackageName());
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
                        Toast.makeText(getContext(), "附近暂无搜索结果", Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                Toast.makeText(getContext(), "附近暂无搜索结果", Toast.LENGTH_LONG).show();
            }
        } else {
            MyToast.error(getContext(), rCode + "");
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
                            new DrivingRouteOverlay(getContext(), aNavMap,//第一个参数是context，2.是地图
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
    }

    @Override
    protected String[] getNeedPermissions() {
        return new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
        };
    }

    @Override
    protected void setListener() {

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

    @Override
    public void onDetach() {
        super.onDetach();
    }

}

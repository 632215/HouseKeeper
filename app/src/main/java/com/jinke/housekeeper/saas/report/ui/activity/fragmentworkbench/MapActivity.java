package com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.saas.report.bean.MapPointBean;
import com.jinke.housekeeper.saas.report.presenter.MapActivityPresenter;
import com.jinke.housekeeper.saas.report.util.SharedPreferencesUtils;
import com.jinke.housekeeper.saas.report.util.ToastUtils;
import com.jinke.housekeeper.saas.report.view.MapActivityView;
import com.jinke.housekeeper.saas.report.helper.LocationService;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.tencent.stat.StatService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;

/**
 * Created by 32 on 17-4-19.
 */

public class MapActivity extends BaseActivity<MapActivityView, MapActivityPresenter> implements View.OnClickListener, MapActivityView {
    @Bind(R.id.customtitle_back)
    ImageView custom_title_back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.startTime)
    TextView tv_start_time;
    @Bind(R.id.endTime)
    TextView tv_end_time;
    @Bind(R.id.mMapView)
    MapView mMapView;
    private BaiduMap mBaiduMap = null;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private List<MapPointBean.ListBean> mapPointList = new ArrayList<>();
    private LocationService locationService;
    private boolean isFirstLoc = true;// 是否首次定位
    private double latitude = 0.0;
    private double longitude = 0.0;

    private double latitudes = 0.0;
    private double longitudes = 0.0;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_map;
    }

    @Override
    protected void initView() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ToastUtils.showLongToast("请打开定位权限！");
        } else {
            locationService = MyApplicaption.getInstance().locationService;
            getTime();
            tv_start_time.setText(startTimeShow);
            tv_end_time.setText(endTimeShow);
            custom_title_back.setOnClickListener(this);
            tv_start_time.setOnClickListener(this);
            tv_end_time.setOnClickListener(this);
            initMap();
            getMapPoint();
        }
    }

    private void initMap() {
        mLocationClient = new LocationClient(getApplicationContext());
        //定位参数的设定
        mLocationClient.setLocOption(locationService.getDefaultLocationClientOption());
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true); //开启定位图层
        mLocationClient.registerLocationListener(myListener);
        mLocationClient.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.customtitle_back:
                locationService.stop();
                locationService.unregisterListener(myListener);
                finish();
                break;
            case R.id.startTime:
                selecterStartTime();
                break;
            case R.id.endTime:
                selecterEndTime();
                break;
        }
    }

    private void getMapPoint() {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("userId", MyApplicaption.getUserId());
        map.put("sessionId", SharedPreferencesUtils.init(this).getString("quality_sessionId"));
        map.put("orgId", CommonlyUtils.getUserInfo(this).getLeftOrgId());
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        presenter.getMapPoint(map);
    }

    @Override
    public void getMapPointOnNext(MapPointBean info) {
        title.setText(info.getOrg().getName());
        latitude = info.getOrg().getLat();// 纬度
        longitude = info.getOrg().getLot();// 经度
        mapPointList = info.getList();
        makeMarker(mapPointList);
    }

    @Override
    public void getMapPointOnError(String code, String msg) {
        makeMarker(mapPointList);
        com.blankj.utilcode.util.ToastUtils.showShort(msg);
        CommonlyUtils.errorState(MapActivity.this, code);
    }



    //地图定位监听
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            latitudes = location.getLatitude();
            longitudes = location.getLongitude();
            if (location == null || mBaiduMap == null)
                return;
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    .direction(10)
                    .latitude(String.valueOf(latitude).equals("0.0") ? latitudes : latitude)
                    .longitude(String.valueOf(longitude).equals("0.0") ? longitudes : longitude)
                    .build();
            mBaiduMap.setMyLocationData(locData);    //设置定位数据
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(String.valueOf(latitude).equals("0.0") ? latitudes : latitude,
                        String.valueOf(longitude).equals("0.0") ? longitudes : longitude);
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, 18);   //设置地图中心点以及缩放级别
                mBaiduMap.animateMapStatus(u);
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }


    private void makeMarker(List<MapPointBean.ListBean> mapPointList) {
        for (MapPointBean.ListBean bean : mapPointList) {
            //定义Maker坐标点
            LatLng point = new LatLng(Double.valueOf(bean.getLat()).doubleValue(), Double.valueOf(bean.getLnt()).doubleValue());
            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.xiaoyuan);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap);
            //在地图上添加Marker，并显示
            mBaiduMap.addOverlay(option);
        }
    }


    private int mYear;
    private int mMonth;
    private int mDay;
    private String startTime = "";
    private String startTimeShow = "";
    private String endTime = "";
    private String endTimeShow = "";

    public void selecterStartTime() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                mYear = year;
                mMonth = month;
                mDay = day;
                int m = (mMonth + 1) < 10 ? 0 + (mMonth + 1) : (mMonth + 1);
                int d = (mDay < 10) ? 0 + mDay : mDay;
                startTime = mYear + "-" + m + "-" + d;
                startTimeShow = m + "." + d;
                tv_start_time.setText(startTimeShow);
                getMapPoint();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void selecterEndTime() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                mYear = year;
                mMonth = month;
                mDay = day;
                int m = (mMonth + 1) < 10 ? 0 + (mMonth + 1) : (mMonth + 1);
                int d = (mDay < 10) ? 0 + mDay : mDay;
                endTime = mYear + "-" + m + "-" + d;
                endTimeShow = m + "." + d;
                tv_end_time.setText(endTimeShow);
                getMapPoint();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void getTime() {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 0);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        startTime = dateString;
        endTime = dateString;
        SimpleDateFormat formatterYear = new SimpleDateFormat("yyyy");
        String yearString = formatterYear.format(date);
        SimpleDateFormat formatterMonth = new SimpleDateFormat("MM");
        String monthString = formatterMonth.format(date);
        SimpleDateFormat formatterDay = new SimpleDateFormat("dd");
        String dayString = formatterDay.format(date);
        startTimeShow = monthString + "." + dayString;
        endTimeShow = monthString + "." + dayString;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMapView != null) {
            mMapView.onDestroy();
        }
    }

    @Override
    public MapActivityPresenter initPresenter() {
        return new MapActivityPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.onResume();
        }
        StatService.onResume(MapActivity.this);
        StatService.trackBeginPage(MapActivity.this, "地图页面");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMapView != null) {
            mMapView.onPause();
        }
        StatService.onPause(MapActivity.this);
        StatService.trackEndPage(MapActivity.this, "地图页面");
    }
}

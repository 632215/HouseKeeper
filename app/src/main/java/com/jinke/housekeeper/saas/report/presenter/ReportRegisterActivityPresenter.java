package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.bean.BackUpsInfo;
import com.jinke.housekeeper.saas.report.helper.LocationService;
import com.jinke.housekeeper.saas.report.service.ReportRegisterActivityBiz;
import com.jinke.housekeeper.saas.report.service.impl.ReportRegistActivityImpl;
import com.jinke.housekeeper.saas.report.service.listener.ReportRegistActivityListener;
import com.jinke.housekeeper.saas.report.util.SharedPreferencesUtils;
import com.jinke.housekeeper.saas.report.view.ReportRegistActivityView;
import com.lidroid.xutils.http.RequestParams;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2017/9/11.
 */

public class ReportRegisterActivityPresenter extends BasePresenter<ReportRegistActivityView> implements ReportRegistActivityListener, BDLocationListener {
    private Context context;
    private ReportRegisterActivityBiz biz;

    public ReportRegisterActivityPresenter(Context context) {
        this.context = context;
        biz = new ReportRegistActivityImpl(context);
    }

    public void upLoadData(RequestParams params, BackUpsInfo backUpsInfo) {
        mView.showLoading();
        biz.upLoadData(params, backUpsInfo, this);
    }

    @Override
    public void upLoadDataResult(String s) {
        if (mView != null) {
            mView.hideLoading();
            mView.upLoadDataResult(s);
        }

    }

    @Override
    public void showMessage(String errorMsg) {
        if (mView != null) {
            mView.hideLoading();
            mView.showMessage(errorMsg);
        }
    }

    public LocationClient mLocationClient = null;
    private LocationService locationService;

    public void initLocation() {
        //初始化百度地图定位
        locationService = MyApplicaption.getInstance().locationService;
        mLocationClient = new LocationClient(context);
        //定位参数的设定
        mLocationClient.setLocOption(locationService.getDefaultLocationClientOption());
        mLocationClient.registerLocationListener(this);//声明LocationClient类
        mLocationClient.start();
    }


    //地图定位监听
    @Override
    public void onReceiveLocation(BDLocation location) {
        if (location != null) {
            DecimalFormat df = new DecimalFormat("#.00000000");
            SharedPreferencesUtils.init(context)
                    .put("latitude", df.format(location.getLatitude()))
                    .put("lontitude", df.format(location.getLongitude()))
                    .put("address", location.getAddrStr() + "");
            if (mLocationClient != null && mLocationClient.isStarted()) {
                mLocationClient.stop();
            }
        }
    }

    @Override
    public void onConnectHotSpotMessage(String s, int i) {
    }
}

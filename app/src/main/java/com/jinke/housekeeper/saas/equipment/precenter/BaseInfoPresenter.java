package com.jinke.housekeeper.saas.equipment.precenter;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.saas.equipment.base.BasePresenter;
import com.jinke.housekeeper.saas.equipment.bean.DeviceInfoBean;
import com.jinke.housekeeper.saas.equipment.config.EquipmentConfig;
import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestListener;
import com.jinke.housekeeper.saas.equipment.service.BaseInfoBiz;
import com.jinke.housekeeper.saas.equipment.service.impl.BaseInfoBizIml;
import com.jinke.housekeeper.saas.equipment.view.BaseInfoView;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/9/30
 */

public class BaseInfoPresenter extends BasePresenter<BaseInfoView> implements OnRequestListener<DeviceInfoBean> {

    private BaseInfoBiz requestBiz;

    public BaseInfoPresenter(Context mContext) {
        requestBiz = new BaseInfoBizIml(mContext);
    }

    public void getDeviceInfo(Map<String, String> map) {
        if (null != mView) {
            if (null != EquipmentConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("projectId", EquipmentConfig.getProjectId());
                map.put("token", EquipmentConfig.getTokenBean().getToken());
                requestBiz.getDeviceInfo(map, this);
            }else {
                mView.onError("err:000");//token为空
            }
        }
    }

    @Override
    public void onSuccess(DeviceInfoBean deviceInfoBean) {
        if (null != mView)
            mView.getDeviceInfo(deviceInfoBean);
    }

    @Override
    public void onError(String Code, String Msg) {
        if (null != mView)
            mView.onError(Msg);
    }
}

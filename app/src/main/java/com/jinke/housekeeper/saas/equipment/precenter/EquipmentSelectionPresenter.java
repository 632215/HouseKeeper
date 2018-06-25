package com.jinke.housekeeper.saas.equipment.precenter;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.saas.equipment.base.BasePresenter;
import com.jinke.housekeeper.saas.equipment.bean.DeviceTypeBean;
import com.jinke.housekeeper.saas.equipment.config.EquipmentConfig;
import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestListener;
import com.jinke.housekeeper.saas.equipment.service.EquipmentSelectionBiz;
import com.jinke.housekeeper.saas.equipment.service.impl.EquipmentSelectionBizIml;
import com.jinke.housekeeper.saas.equipment.view.EquipmentSelectionView;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/9/29
 */

public class EquipmentSelectionPresenter extends BasePresenter<EquipmentSelectionView> implements OnRequestListener<DeviceTypeBean> {

    private EquipmentSelectionBiz requestBiz;

    public EquipmentSelectionPresenter(Context mContext) {
        requestBiz = new EquipmentSelectionBizIml(mContext);
    }

    public void getDeviceType(Map<String, String> map) {
        if (null != mView) {
            if (null != EquipmentConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("token", EquipmentConfig.getTokenBean().getToken());
                requestBiz.getDeviceType(map, this);
            }else {
                mView.onError("err:000");//token为空
            }
        }
    }

    @Override
    public void onSuccess(DeviceTypeBean deviceTypeBean) {
        if (null != mView){
            mView.getDeviceTypeSuccess(deviceTypeBean);
        }
    }

    @Override
    public void onError(String Code, String Msg) {
        if (null != mView)
            mView.onError(Msg);
    }
}

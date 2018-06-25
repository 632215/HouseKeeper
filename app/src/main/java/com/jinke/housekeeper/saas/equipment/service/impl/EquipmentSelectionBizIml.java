package com.jinke.housekeeper.saas.equipment.service.impl;

import android.content.Context;

import com.jinke.housekeeper.saas.equipment.bean.DeviceTypeBean;
import com.jinke.housekeeper.saas.equipment.bean.HttpResult;
import com.jinke.housekeeper.saas.equipment.http.HttpMethods;
import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestListener;
import com.jinke.housekeeper.saas.equipment.http.ProgressSubscriber;
import com.jinke.housekeeper.saas.equipment.http.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.equipment.service.EquipmentSelectionBiz;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/9/29
 */

public class EquipmentSelectionBizIml implements EquipmentSelectionBiz {

    private Context mContext;

    public EquipmentSelectionBizIml(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getDeviceType(Map<String, String> map, final OnRequestListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<DeviceTypeBean>() {

            @Override
            public void onNext(DeviceTypeBean deviceTypeBean) {
                listener.onSuccess(deviceTypeBean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().getDeviceType(new ProgressSubscriber<HttpResult<DeviceTypeBean>>(onNextListener, mContext), map);
    }

}

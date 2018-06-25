package com.jinke.housekeeper.saas.equipment.service.impl;

import android.content.Context;

import com.jinke.housekeeper.saas.equipment.bean.DeviceInfoBean;
import com.jinke.housekeeper.saas.equipment.bean.HttpResult;
import com.jinke.housekeeper.saas.equipment.http.HttpMethods;
import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestListener;
import com.jinke.housekeeper.saas.equipment.http.ProgressSubscriber;
import com.jinke.housekeeper.saas.equipment.http.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.equipment.service.BaseInfoBiz;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/9/30
 */

public class BaseInfoBizIml implements BaseInfoBiz {

    private Context mContext;

    public BaseInfoBizIml(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public void getDeviceInfo(Map<String, String> map, final OnRequestListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<DeviceInfoBean>() {

            @Override
            public void onNext(DeviceInfoBean deviceInfoBean) {
                listener.onSuccess(deviceInfoBean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().getDeviceInfo(new ProgressSubscriber<HttpResult<DeviceInfoBean>>(onNextListener, mContext), map);
    }
}

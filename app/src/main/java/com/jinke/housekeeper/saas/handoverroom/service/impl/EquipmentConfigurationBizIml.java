package com.jinke.housekeeper.saas.handoverroom.service.impl;

import android.content.Context;

import com.jinke.housekeeper.saas.handoverroom.bean.EmptyBean;
import com.jinke.housekeeper.saas.handoverroom.bean.FindListDataBean;
import com.jinke.housekeeper.saas.handoverroom.bean.HttpResult;
import com.jinke.housekeeper.saas.handoverroom.http.HttpMethods;
import com.jinke.housekeeper.saas.handoverroom.http.ProgressSubscriber;
import com.jinke.housekeeper.saas.handoverroom.http.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.handoverroom.http.listener.OnBindingDeviceRequestListener;
import com.jinke.housekeeper.saas.handoverroom.http.listener.OnRequestListener;
import com.jinke.housekeeper.saas.handoverroom.service.EquipmentConfigurationBiz;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/12/1
 */

public class EquipmentConfigurationBizIml  implements EquipmentConfigurationBiz {

    private Context mContext;

    public EquipmentConfigurationBizIml(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void findListData(Map<String, String> map, final OnRequestListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<FindListDataBean>() {

            @Override
            public void onNext(FindListDataBean findListDataBean) {
                listener.onSuccess(findListDataBean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().findListData(new ProgressSubscriber<HttpResult<FindListDataBean>>(onNextListener, mContext), map);
    }

    @Override
    public void bindingDevice(Map<String, String> map, final OnBindingDeviceRequestListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<EmptyBean>() {

            @Override
            public void onNext(EmptyBean emptyBean) {
                listener.onBindingDeviceSuccess(emptyBean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().bindingDevice(new ProgressSubscriber<HttpResult<EmptyBean>>(onNextListener, mContext), map);
    }
}

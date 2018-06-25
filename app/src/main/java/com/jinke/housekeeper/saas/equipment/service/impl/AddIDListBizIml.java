package com.jinke.housekeeper.saas.equipment.service.impl;

import android.content.Context;

import com.jinke.housekeeper.saas.equipment.bean.AddPointCallBackBean;
import com.jinke.housekeeper.saas.equipment.bean.DeviceInfoBean;
import com.jinke.housekeeper.saas.equipment.bean.EmptyBean;
import com.jinke.housekeeper.saas.equipment.bean.HttpResult;
import com.jinke.housekeeper.saas.equipment.http.HttpMethods;
import com.jinke.housekeeper.saas.equipment.http.ProgressSubscriber;
import com.jinke.housekeeper.saas.equipment.http.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestListener;
import com.jinke.housekeeper.saas.equipment.service.AddIDListBiz;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/10/10
 */

public class AddIDListBizIml implements AddIDListBiz {

    private Context mContext;

    public AddIDListBizIml(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getAddPoint(Map<String, String> map, final OnRequestListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<AddPointCallBackBean>() {

            @Override
            public void onNext(AddPointCallBackBean addPointCallBackBean) {
                listener.onSuccess(addPointCallBackBean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().getAddPoint(new ProgressSubscriber<HttpResult<AddPointCallBackBean>>(onNextListener, mContext), map);
    }
}

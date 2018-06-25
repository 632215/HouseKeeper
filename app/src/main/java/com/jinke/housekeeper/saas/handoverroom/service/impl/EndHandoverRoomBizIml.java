package com.jinke.housekeeper.saas.handoverroom.service.impl;

import android.content.Context;

import com.jinke.housekeeper.saas.handoverroom.bean.EmptyBean;
import com.jinke.housekeeper.saas.handoverroom.bean.HttpResult;
import com.jinke.housekeeper.saas.handoverroom.http.HttpMethods;
import com.jinke.housekeeper.saas.handoverroom.http.ProgressSubscriber;
import com.jinke.housekeeper.saas.handoverroom.http.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.handoverroom.http.listener.OnRequestListener;
import com.jinke.housekeeper.saas.handoverroom.service.BeganHandoverRoomBiz;
import com.jinke.housekeeper.saas.handoverroom.service.EndHandoverRoomBiz;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/11/26
 */

public class EndHandoverRoomBizIml implements EndHandoverRoomBiz {

    private Context mContext;

    public EndHandoverRoomBizIml(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void takeHouse(Map<String, String> map, final OnRequestListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<EmptyBean>() {

            @Override
            public void onNext(EmptyBean emptyBean) {
                listener.onSuccess(emptyBean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().takeHouse(new ProgressSubscriber<HttpResult<EmptyBean>>(onNextListener, mContext), map);
    }
}
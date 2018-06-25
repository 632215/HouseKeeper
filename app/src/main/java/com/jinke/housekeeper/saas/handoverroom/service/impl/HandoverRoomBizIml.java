package com.jinke.housekeeper.saas.handoverroom.service.impl;

import android.content.Context;

import com.jinke.housekeeper.saas.handoverroom.bean.HttpResult;
import com.jinke.housekeeper.saas.handoverroom.bean.TokenBean;
import com.jinke.housekeeper.saas.handoverroom.http.HttpMethods;
import com.jinke.housekeeper.saas.handoverroom.http.ProgressSubscriber;
import com.jinke.housekeeper.saas.handoverroom.http.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.handoverroom.http.listener.OnRequestListener;
import com.jinke.housekeeper.saas.handoverroom.service.HandoverRoomBiz;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/11/26
 */

public class HandoverRoomBizIml implements HandoverRoomBiz {

    private Context mContext;

    public HandoverRoomBizIml(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getUserToken(Map<String, String> map, final OnRequestListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<TokenBean>() {

            @Override
            public void onNext(TokenBean tokenBean) {
                listener.onSuccess(tokenBean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().getUserToken(new ProgressSubscriber<HttpResult<TokenBean>>(onNextListener, mContext), map);
    }
}

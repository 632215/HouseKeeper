package com.jinke.housekeeper.saas.handoverroom.service.impl;

import android.content.Context;

import com.jinke.housekeeper.saas.handoverroom.bean.EmptyBean;
import com.jinke.housekeeper.saas.handoverroom.bean.FindStateBean;
import com.jinke.housekeeper.saas.handoverroom.bean.HandoverRoomBean;
import com.jinke.housekeeper.saas.handoverroom.bean.HttpResult;
import com.jinke.housekeeper.saas.handoverroom.bean.TokenBean;
import com.jinke.housekeeper.saas.handoverroom.http.HttpMethods;
import com.jinke.housekeeper.saas.handoverroom.http.ProgressSubscriber;
import com.jinke.housekeeper.saas.handoverroom.http.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.handoverroom.http.listener.OnFindStateRequestListener;
import com.jinke.housekeeper.saas.handoverroom.http.listener.OnGetHouseInfoRequestListener;
import com.jinke.housekeeper.saas.handoverroom.http.listener.OnRequestListener;
import com.jinke.housekeeper.saas.handoverroom.service.BeganHandoverRoomBiz;
import com.jinke.housekeeper.saas.handoverroom.service.HandoverRoomBiz;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/11/26
 */

public class BeganHandoverRoomBizIml implements BeganHandoverRoomBiz {

    private Context mContext;

    public BeganHandoverRoomBizIml(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void handleHouse(Map<String, String> map, final OnRequestListener listener) {
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

        HttpMethods.getInstance().handleHouse(new ProgressSubscriber<HttpResult<EmptyBean>>(onNextListener, mContext), map);
    }

    @Override
    public void getHouseInfo(Map<String, String> map, final OnGetHouseInfoRequestListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<HandoverRoomBean>() {

            @Override
            public void onNext(HandoverRoomBean handoverRoomBean) {
                listener.getHouseInfoSuccess(handoverRoomBean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().getHouseInfo(new ProgressSubscriber<HttpResult<HandoverRoomBean>>(onNextListener, mContext), map);
    }

    @Override
    public void findState(Map<String, String> map, final OnFindStateRequestListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<FindStateBean>() {

            @Override
            public void onNext(FindStateBean findStateBean) {
                listener.findStateSuccess(findStateBean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().findState(new ProgressSubscriber<HttpResult<FindStateBean>>(onNextListener, mContext), map);
    }
}
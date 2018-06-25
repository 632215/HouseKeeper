package com.jinke.housekeeper.saas.equipment.service.impl;

import android.content.Context;

import com.jinke.housekeeper.saas.equipment.bean.EmptyBean;
import com.jinke.housekeeper.saas.equipment.bean.HttpResult;
import com.jinke.housekeeper.saas.equipment.bean.ParameterBean;
import com.jinke.housekeeper.saas.equipment.bean.ReadWatchBean;
import com.jinke.housekeeper.saas.equipment.http.HttpMethods;
import com.jinke.housekeeper.saas.equipment.http.ProgressSubscriber;
import com.jinke.housekeeper.saas.equipment.http.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.equipment.http.listener.CompleteTaskOnRequestListener;
import com.jinke.housekeeper.saas.equipment.http.listener.GetParameterOnRequestListener;
import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestListener;
import com.jinke.housekeeper.saas.equipment.service.ThreeTableListBiz;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/11/11
 */

public class ThreeTableBizIml implements ThreeTableListBiz {

    private Context mContext;
    private Map<String, String> map;
    private OnRequestListener<ReadWatchBean> listener;

    public ThreeTableBizIml(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void readWatch(Map<String, String> map, final OnRequestListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<ReadWatchBean>() {

            @Override
            public void onNext(ReadWatchBean readWatchBean) {
                listener.onSuccess(readWatchBean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().readWatch(new ProgressSubscriber<HttpResult<ReadWatchBean>>(onNextListener, mContext), map);
    }

    @Override
    public void getParameter(Map<String, String> map, final GetParameterOnRequestListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<ParameterBean>() {

            @Override
            public void onNext(ParameterBean parameterBean) {
                listener.onGetParameterSuccess(parameterBean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().getParameter(new ProgressSubscriber<HttpResult<ParameterBean>>(onNextListener, mContext), map);
    }

    @Override
    public void uploadData(Map<String, String> map, final CompleteTaskOnRequestListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {

            @Override
            public void onNext(Object o) {
                listener.onUploadDataSuccess(o);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().uploadData(new ProgressSubscriber<HttpResult<EmptyBean>>(onNextListener, mContext), map);
    }
}

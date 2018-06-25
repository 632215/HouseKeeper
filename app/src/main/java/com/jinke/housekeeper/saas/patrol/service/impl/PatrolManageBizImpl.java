package com.jinke.housekeeper.saas.patrol.service.impl;

import android.content.Context;

import com.jinke.housekeeper.saas.patrol.bean.EmptyBean;
import com.jinke.housekeeper.saas.patrol.bean.HttpResult;
import com.jinke.housekeeper.saas.patrol.bean.PointBean;
import com.jinke.housekeeper.saas.patrol.bean.TimeOutTaskListBean;
import com.jinke.housekeeper.saas.patrol.http.HttpMethods;
import com.jinke.housekeeper.saas.patrol.listener.OnDeletePointListener;
import com.jinke.housekeeper.saas.patrol.listener.OnRequestListener;
import com.jinke.housekeeper.saas.patrol.http.ProgressSubscriber;
import com.jinke.housekeeper.saas.patrol.http.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.patrol.listener.OnTimeOutTaskListener;
import com.jinke.housekeeper.saas.patrol.service.PatrolManageBiz;

import java.util.List;
import java.util.Map;

/**
 * Created by root on 17-7-24.
 */

public class PatrolManageBizImpl implements PatrolManageBiz {

    private Context mContext;

    public PatrolManageBizImpl(Context mContext) {
        this.mContext = mContext;

    }

    @Override
    public void getPointList(Map<String, String> map, final OnRequestListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<PointBean>() {
            @Override
            public void onNext(PointBean bean) {
                listener.onSuccess(bean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getPointList(new ProgressSubscriber<HttpResult<PointBean>>(onNextListener, mContext), map);
    }

    @Override
    public void getTimeOutTask(Map<String, String> map, final OnTimeOutTaskListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<List<TimeOutTaskListBean>>() {
            @Override
            public void onNext(List<TimeOutTaskListBean> bean) {
                listener.onTimeOutTaskSuccess(bean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getTimeOutTask(new ProgressSubscriber<HttpResult<List<TimeOutTaskListBean>>>(onNextListener, mContext), map);
    }

    @Override
    public void delPoint(Map<String, String> map, final OnDeletePointListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<EmptyBean>() {
            @Override
            public void onNext(EmptyBean bean) {
                listener.onDeleteSuccess(bean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().delPoint(new ProgressSubscriber<HttpResult<EmptyBean>>(onNextListener, mContext), map);
    }
}

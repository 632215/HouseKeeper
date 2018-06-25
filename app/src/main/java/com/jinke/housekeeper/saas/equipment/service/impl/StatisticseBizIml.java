package com.jinke.housekeeper.saas.equipment.service.impl;

import android.content.Context;

import com.jinke.housekeeper.saas.equipment.bean.ElectricDataBean;
import com.jinke.housekeeper.saas.equipment.bean.ElectricMonthData;
import com.jinke.housekeeper.saas.equipment.bean.HttpResult;
import com.jinke.housekeeper.saas.equipment.bean.ReadWatchBean;
import com.jinke.housekeeper.saas.equipment.http.HttpMethods;
import com.jinke.housekeeper.saas.equipment.http.ProgressSubscriber;
import com.jinke.housekeeper.saas.equipment.http.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.equipment.http.listener.GetElectricMonthDataOnRequestListener;
import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestListener;
import com.jinke.housekeeper.saas.equipment.service.StatisticseBiz;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/11/18
 */

public class StatisticseBizIml implements StatisticseBiz {

    private Context mContext;
    private Map<String, String> map;
    private OnRequestListener<ReadWatchBean> listener;

    public StatisticseBizIml(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getElectricData(Map<String, String> map, final OnRequestListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<ElectricDataBean>() {

            @Override
            public void onNext(ElectricDataBean electricDataBean) {
                listener.onSuccess(electricDataBean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().getElectricData(new ProgressSubscriber<HttpResult<ElectricDataBean>>(onNextListener, mContext), map);
    }

    @Override
    public void getElectricMonthData(Map<String, String> map, final GetElectricMonthDataOnRequestListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<ElectricMonthData>() {

            @Override
            public void onNext(ElectricMonthData electricMonthData) {
                listener.getElectricMonthDataSuccess(electricMonthData);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().getElectricMonthData(new ProgressSubscriber<HttpResult<ElectricMonthData>>(onNextListener, mContext), map);
    }
}

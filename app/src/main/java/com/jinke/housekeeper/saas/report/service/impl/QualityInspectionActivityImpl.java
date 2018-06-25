package com.jinke.housekeeper.saas.report.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.saas.report.bean.TodayChangeInfo;
import com.jinke.housekeeper.saas.report.bean.TodayWorkInfo;
import com.jinke.housekeeper.saas.report.http.HttpMethods;
import com.jinke.housekeeper.saas.report.http.HttpResult;
import com.jinke.housekeeper.saas.report.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.saas.report.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.report.service.QualityInspectionActivityBiz;
import com.jinke.housekeeper.saas.report.service.listener.QualityInspectionActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class QualityInspectionActivityImpl implements QualityInspectionActivityBiz {
    private Context context;

    public QualityInspectionActivityImpl(Context context) {
        this.context = context;
    }

    @Override
    public void gettodayWork(SortedMap<String, String> map, final QualityInspectionActivityListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<TodayWorkInfo>() {
            @Override
            public void onNext(TodayWorkInfo info) {
                if (info != null) {
                listener.getToDayWorkOnNext(info);
                }
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getToDayWorkOnError(Code,Msg);
            }
        };
        HttpMethods.getInstance().getTodayWorkData(new ProgressSubscriber<HttpResult<TodayWorkInfo>>(onNextListener, context, false), map, MyApplicaption.createSign(map));
    }

    @Override
    public void gettodayChange(SortedMap<String, String> map, final QualityInspectionActivityListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<TodayChangeInfo>() {
            @Override
            public void onNext(TodayChangeInfo info) {
                if (info != null) {
                    listener.getToDayChangeOnNext(info);
                }
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getToDayChangeOnError(Code,Msg);
            }
        };
        HttpMethods.getInstance().getTodayChangeData(new ProgressSubscriber<HttpResult<TodayChangeInfo>>(onNextListener, context, false), map, MyApplicaption.createSign(map));
    }
}

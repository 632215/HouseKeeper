package com.jinke.housekeeper.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.bean.ChangTotalInfo;
import com.jinke.housekeeper.bean.StatisticsInfo;
import com.jinke.housekeeper.bean.StatisticstInfo;
import com.jinke.housekeeper.http.HttpMethods;
import com.jinke.housekeeper.http.HttpResult;
import com.jinke.housekeeper.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.service.listener.ProjectSFragmentListener;
import com.jinke.housekeeper.service.biz.ProjectSFragmentBiz;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/7.
 */

public class ProjectSFragmentImpl implements ProjectSFragmentBiz {
    private Context mContext;

    public ProjectSFragmentImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getChangTotal(SortedMap<String, String> map, final ProjectSFragmentListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<ChangTotalInfo>() {
            @Override
            public void onNext(ChangTotalInfo info) {
                listener.getChangTotalNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getChangTotalError(Code,  Msg);
            }
        };
        HttpMethods.getInstance().getChangTotal(new ProgressSubscriber<HttpResult<ChangTotalInfo>>(onNextListener, mContext, false), map, MyApplicaption.createSign(map));
    }

    @Override
    public void getStatistics(SortedMap<String, String> map,final ProjectSFragmentListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<StatisticsInfo>() {
            @Override
            public void onNext(StatisticsInfo info) {
                listener.getStatisticsNext(info);

            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getStatisticsError(Code,  Msg);
            }
        };
        HttpMethods.getInstance().getStatistics(new ProgressSubscriber<HttpResult<StatisticsInfo>>(onNextListener, mContext, false), map, MyApplicaption.createSign(map));
    }

    @Override
    public void getStatisticst(SortedMap<String, String> map, final ProjectSFragmentListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<StatisticstInfo>() {
            @Override
            public void onNext(StatisticstInfo info) {
                listener.getStatisticstNext(info);

            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getStatisticstError(Code,  Msg);
            }
        };
        HttpMethods.getInstance().getStatisticst(new ProgressSubscriber<HttpResult<StatisticstInfo>>(onNextListener,mContext, false), map, MyApplicaption.createSign(map));
    }
}

package com.jinke.housekeeper.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.bean.StatisticsgrInfo;
import com.jinke.housekeeper.service.listener.PersonalFragmentListener;
import com.jinke.housekeeper.service.biz.PersonalFragmentBiz;
import com.jinke.housekeeper.http.HttpMethods;
import com.jinke.housekeeper.http.HttpResult;
import com.jinke.housekeeper.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.http.progress.SubscriberOnNextListener;
import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/7.
 */

public class PersonalFragmentImpl implements PersonalFragmentBiz {
    private Context context;

    public PersonalFragmentImpl(Context context) {
        this.context = context;
    }

    /**
     *获取巡查问题数据
     * @param getStatisticsgr
     * @param listener
     */
    @Override
    public void getStatisticsgr(SortedMap<String, String> getStatisticsgr, final PersonalFragmentListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<StatisticsgrInfo>() {
            @Override
            public void onNext(StatisticsgrInfo info) {
                listener.getStatisticsgrNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getStatisticsgrError(Code,Msg);

            }
        };
        HttpMethods.getInstance().getStatisticsgr(new ProgressSubscriber<HttpResult<StatisticsgrInfo>>(onNextListener, context, false), getStatisticsgr, MyApplicaption.createSign(getStatisticsgr));
    }

    /**
     *
     * @param map
     * @param listener
     */
    @Override
    public void getIndStaSponIns(SortedMap<String, String> map, final PersonalFragmentListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<StatisticsgrInfo>() {
            @Override
            public void onNext(StatisticsgrInfo info) {
                listener.getIndStaSponInsNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getIndStaSponInsError(Code,Msg);
            }
        };
        HttpMethods.getInstance().getIndStaSponIns(new ProgressSubscriber<HttpResult<StatisticsgrInfo>>(onNextListener, context, false), map, MyApplicaption.createSign(map));
    }
}

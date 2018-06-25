package com.jinke.housekeeper.saas.report.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.saas.report.bean.SelfHistoryBean;
import com.jinke.housekeeper.saas.report.http.HttpMethods;
import com.jinke.housekeeper.saas.report.http.HttpResult;
import com.jinke.housekeeper.saas.report.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.saas.report.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.report.service.PersonFilterFragmentBiz;
import com.jinke.housekeeper.saas.report.service.listener.PersonFilterFragmentListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class PersonFilterFragmentImpl implements PersonFilterFragmentBiz {
    private Context context;

    public PersonFilterFragmentImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getSelfHistoryList(SortedMap<String, String> map, final PersonFilterFragmentListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<SelfHistoryBean>() {
            @Override
            public void onNext(SelfHistoryBean bean) {
              listener.getSelfHistoryListonNext(bean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getSelfHistoryListonError( Code,  Msg);
            }
        };
        HttpMethods.getInstance().getSelfHistory(new ProgressSubscriber<HttpResult<SelfHistoryBean>>(onNextListener, context, false), map, MyApplicaption.createSign(map));
    }
}

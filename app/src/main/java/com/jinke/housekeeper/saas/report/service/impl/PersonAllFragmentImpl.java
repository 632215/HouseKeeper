package com.jinke.housekeeper.saas.report.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.saas.report.bean.SelfHistoryBean;
import com.jinke.housekeeper.saas.report.config.ReportConfig;
import com.jinke.housekeeper.saas.report.http.HttpMethods;
import com.jinke.housekeeper.saas.report.http.HttpResult;
import com.jinke.housekeeper.saas.report.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.saas.report.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.report.service.PersonAllFragmentBiz;
import com.jinke.housekeeper.saas.report.service.listener.PersonAllFragmentListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class PersonAllFragmentImpl implements PersonAllFragmentBiz {
    private Context context;

    public PersonAllFragmentImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getSelfHistoryList(SortedMap<String, String> map, final PersonAllFragmentListener listener) {
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
        HttpMethods.getInstance().getSelfHistory(new ProgressSubscriber<HttpResult<SelfHistoryBean>>(onNextListener, context, false), map, ReportConfig.createSign(map));
    }
}

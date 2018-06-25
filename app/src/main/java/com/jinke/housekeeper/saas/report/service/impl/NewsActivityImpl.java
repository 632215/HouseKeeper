package com.jinke.housekeeper.saas.report.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.saas.report.bean.NoReadBean;
import com.jinke.housekeeper.saas.report.http.HttpMethods;
import com.jinke.housekeeper.saas.report.http.HttpResult;
import com.jinke.housekeeper.saas.report.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.saas.report.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.report.service.NewsActivityBiz;
import com.jinke.housekeeper.saas.report.service.listener.NewsActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/7.
 */

public class NewsActivityImpl implements NewsActivityBiz {
    private Context context;

    public NewsActivityImpl(Context context) {
        this.context = context;
    }

    @Override
    public void updateReadStatus(SortedMap<String, String> map, final NewsActivityListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<NoReadBean>() {

            @Override
            public void onNext(NoReadBean info) {
                if (null != info.getList()) {
                    listener.updateReadStatusNext(info);
                }
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.updateReadStatusError( Code,  Msg);
            }
        };
        HttpMethods.getInstance().getNoRead(new ProgressSubscriber<HttpResult<NoReadBean>>(onNextListener, context, true), map, MyApplicaption.createSign(map));
    }
}

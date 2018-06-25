package com.jinke.housekeeper.saas.report.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.saas.report.bean.KeyPointBean;
import com.jinke.housekeeper.saas.report.config.ReportConfig;
import com.jinke.housekeeper.saas.report.http.HttpMethods;
import com.jinke.housekeeper.saas.report.http.HttpResult;
import com.jinke.housekeeper.saas.report.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.saas.report.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.report.service.KeyPointsActivityBiz;
import com.jinke.housekeeper.saas.report.service.listener.KeyPointsActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class KeyPointsActivityImpl implements KeyPointsActivityBiz {
    private Context context;

    public KeyPointsActivityImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getGJContentData(SortedMap<String, String> map, final KeyPointsActivityListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<KeyPointBean>() {
            @Override
            public void onNext(KeyPointBean info) {
               listener.getGJContentDataOnNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getGJContentDataOnError(Code,  Msg);

            }
        };

        HttpMethods.getInstance().getKeyPoint(new ProgressSubscriber<HttpResult<KeyPointBean>>(onNextListener, context, false), map, ReportConfig.createSign(map));

    }
}

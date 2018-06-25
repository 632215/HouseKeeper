package com.jinke.housekeeper.saas.report.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.bean.EmptyBean;
import com.jinke.housekeeper.saas.report.bean.NodeDetailBean;
import com.jinke.housekeeper.saas.report.config.ReportConfig;
import com.jinke.housekeeper.saas.report.http.HttpMethods;
import com.jinke.housekeeper.saas.report.http.HttpResult;
import com.jinke.housekeeper.saas.report.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.saas.report.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.report.service.FoundProblemActivityBiz;
import com.jinke.housekeeper.saas.report.service.listener.FoundProblemActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class FoundProblemActivityImpl implements FoundProblemActivityBiz {
    private Context context;

    public FoundProblemActivityImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getProcessDetail(SortedMap<String, String> map, final FoundProblemActivityListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<NodeDetailBean>() {
            @Override
            public void onNext(NodeDetailBean info) {
                if (info.getObj() != null) {
                    listener.getProcessDetailonNext(info);
                }
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getProcessDetailonError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getNodeDetail(new ProgressSubscriber<HttpResult<NodeDetailBean>>(onNextListener, context, true), map, ReportConfig.createSign(map));

    }

    @Override
    public void grabSingle(SortedMap<String, String> map, final FoundProblemActivityListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<EmptyBean>() {
            @Override
            public void onNext(EmptyBean info) {
listener.grabSingleonNext();
                }

            @Override
            public void onError(String Code, String Msg) {
                listener.grabSingleonError(Code,  Msg);

            }
        };
        HttpMethods.getInstance().grabSingle(new ProgressSubscriber<HttpResult<EmptyBean>>(onNextListener, context, false), map, ReportConfig.createSign(map));
    }
}

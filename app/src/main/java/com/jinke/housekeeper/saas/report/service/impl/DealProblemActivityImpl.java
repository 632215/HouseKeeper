package com.jinke.housekeeper.saas.report.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.saas.report.bean.NodeDetailBean;
import com.jinke.housekeeper.saas.report.config.ReportConfig;
import com.jinke.housekeeper.saas.report.http.HttpMethods;
import com.jinke.housekeeper.saas.report.http.HttpResult;
import com.jinke.housekeeper.saas.report.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.saas.report.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.report.service.DealProblemActivityBiz;
import com.jinke.housekeeper.saas.report.service.listener.DealProblemActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/7.
 */

public class DealProblemActivityImpl implements DealProblemActivityBiz {
    private Context context;

    public DealProblemActivityImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getProcessDetail(SortedMap<String, String> map, final DealProblemActivityListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<NodeDetailBean>() {
            @Override
            public void onNext(NodeDetailBean info) {
                if (info.getObj() != null) {
                    listener.getProcessDetailonNext(info);
                }
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getProcessDetailonError( Code,  Msg);

            }
        };

        HttpMethods.getInstance().getNodeDetail(new ProgressSubscriber<HttpResult<NodeDetailBean>>(onNextListener, context, true), map, ReportConfig.createSign(map));

    }
}

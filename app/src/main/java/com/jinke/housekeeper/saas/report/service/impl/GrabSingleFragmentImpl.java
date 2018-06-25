package com.jinke.housekeeper.saas.report.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.saas.report.bean.WorkOrderBean;
import com.jinke.housekeeper.saas.report.config.ReportConfig;
import com.jinke.housekeeper.saas.report.http.HttpMethods;
import com.jinke.housekeeper.saas.report.http.HttpResult;
import com.jinke.housekeeper.saas.report.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.saas.report.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.report.service.GrabSingleFragmentBiz;
import com.jinke.housekeeper.saas.report.service.listener.GrabSingleFragmentListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class GrabSingleFragmentImpl implements GrabSingleFragmentBiz {
    private Context context;

    public GrabSingleFragmentImpl(Context context) {
        this.context = context;
    }

    @Override
    public void initDate(SortedMap<String, String> map, final GrabSingleFragmentListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<WorkOrderBean>() {
            @Override
            public void onNext(WorkOrderBean info) {
                listener.initDateOnNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
              listener.initDateOnError(Code,Msg);
            }
        };
        HttpMethods.getInstance().workOrderLists(new ProgressSubscriber<HttpResult<WorkOrderBean>>(onNextListener, context, true), map, ReportConfig.createSign(map));

    }
}

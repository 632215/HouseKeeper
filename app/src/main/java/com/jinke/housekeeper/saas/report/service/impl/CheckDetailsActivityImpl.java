package com.jinke.housekeeper.saas.report.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.saas.report.bean.ProcessDetailBean;
import com.jinke.housekeeper.saas.report.config.ReportConfig;
import com.jinke.housekeeper.saas.report.service.CheckDetailsActivityBiz;
import com.jinke.housekeeper.saas.report.service.listener.CheckDetailsActivityListener;
import com.jinke.housekeeper.saas.report.http.HttpMethods;
import com.jinke.housekeeper.saas.report.http.HttpResult;
import com.jinke.housekeeper.saas.report.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.saas.report.http.progress.SubscriberOnNextListener;
import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class CheckDetailsActivityImpl implements CheckDetailsActivityBiz {
    private Context context;

    public CheckDetailsActivityImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getProcessDetail(SortedMap<String, String> map, final CheckDetailsActivityListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<ProcessDetailBean>() {
            @Override
            public void onNext(ProcessDetailBean info) {
                listener.getProcessDetailonNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getProcessDetailonError(Code,  Msg);

            }
        };
        HttpMethods.getInstance().getProcessDetail(new ProgressSubscriber<HttpResult<ProcessDetailBean>>(onNextListener,context, true), map, ReportConfig.createSign(map));

    }
}

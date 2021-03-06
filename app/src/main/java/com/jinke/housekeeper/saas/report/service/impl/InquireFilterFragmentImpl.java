package com.jinke.housekeeper.saas.report.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.saas.report.bean.AllReportBean;
import com.jinke.housekeeper.saas.report.config.ReportConfig;
import com.jinke.housekeeper.saas.report.http.HttpMethods;
import com.jinke.housekeeper.saas.report.http.HttpResult;
import com.jinke.housekeeper.saas.report.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.saas.report.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.report.service.InquireFilterFragmentBiz;
import com.jinke.housekeeper.saas.report.service.listener.InquireFilterFragmentListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class InquireFilterFragmentImpl implements InquireFilterFragmentBiz {
    private Context context;

    public InquireFilterFragmentImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getAllReportList(SortedMap<String, String> map, final InquireFilterFragmentListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<AllReportBean>() {
            @Override
            public void onNext(AllReportBean bean) {
                listener.getAllReportListonNext(bean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getAllReportListonError(Code,Msg);
            }
        };
        HttpMethods.getInstance().getAllReport(new ProgressSubscriber<HttpResult<AllReportBean>>(onNextListener, context, false), map, ReportConfig.createSign(map));
    }
}

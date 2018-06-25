package com.jinke.housekeeper.saas.report.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.saas.report.bean.ProcessNodeBean;
import com.jinke.housekeeper.saas.report.config.ReportConfig;
import com.jinke.housekeeper.saas.report.http.HttpMethods;
import com.jinke.housekeeper.saas.report.http.HttpResult;
import com.jinke.housekeeper.saas.report.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.saas.report.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.report.service.RectProcessActivityBiz;
import com.jinke.housekeeper.saas.report.service.listener.RectProcessActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class RectProcessActivityImpl implements RectProcessActivityBiz {
    private Context context;

    public RectProcessActivityImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getDetailsData(SortedMap<String, String> map, final RectProcessActivityListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<ProcessNodeBean>() {

            @Override
            public void onNext(ProcessNodeBean processNodeBean) {
              listener.getDetailsDataonNext(processNodeBean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getDetailsDataonError( Code,  Msg);

            }
        };

        HttpMethods.getInstance().getProcessNode(new ProgressSubscriber<HttpResult<ProcessNodeBean>>(onNextListener,context, false), map, ReportConfig.createSign(map));

    }
}

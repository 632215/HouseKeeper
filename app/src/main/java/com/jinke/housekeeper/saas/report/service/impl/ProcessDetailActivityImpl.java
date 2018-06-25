package com.jinke.housekeeper.saas.report.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.saas.report.bean.NodeElseBean;
import com.jinke.housekeeper.saas.report.bean.WorkBean;
import com.jinke.housekeeper.saas.report.config.ReportConfig;
import com.jinke.housekeeper.saas.report.http.HttpMethods;
import com.jinke.housekeeper.saas.report.http.HttpResult;
import com.jinke.housekeeper.saas.report.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.saas.report.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.report.service.ProcessDetailActivityBiz;
import com.jinke.housekeeper.saas.report.service.listener.ProcessDetailActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class ProcessDetailActivityImpl implements ProcessDetailActivityBiz {
    private Context context;

    public ProcessDetailActivityImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getWorkDetail(SortedMap<String, String> map, final ProcessDetailActivityListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<WorkBean>() {
            @Override
            public void onNext(WorkBean info) {
                WorkBean.ObjBean workBean = info.getObj();
                if (workBean != null) {
                    listener.getWorkDetailonNext(workBean);
                }
            }

            @Override
            public void onError(String Code, String Msg) {
             listener.getWorkDetailonError(Code,Msg);
            }
        };
        HttpMethods.getInstance().getWorkBean(new ProgressSubscriber<HttpResult<WorkBean>>(onNextListener, context, true), map, ReportConfig.createSign(map));

    }

    @Override
    public void getProcessDetail(SortedMap<String, String> map, final ProcessDetailActivityListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<NodeElseBean>() {
            @Override
            public void onNext(NodeElseBean info) {
                if (info != null) {
                    listener.getProcessDetailonNext(info);
                }
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getProcessDetailonError( Code,  Msg);

            }
        };

        HttpMethods.getInstance().getNodeElse(new ProgressSubscriber<HttpResult<NodeElseBean>>(onNextListener, context, true), map, ReportConfig.createSign(map));
    }
}

package com.jinke.housekeeper.saas.patrol.service.impl;

import android.content.Context;

import com.jinke.housekeeper.saas.patrol.bean.ContrastDataBean;
import com.jinke.housekeeper.saas.patrol.bean.HttpResult;
import com.jinke.housekeeper.saas.patrol.bean.PointProjectDataBean;
import com.jinke.housekeeper.saas.patrol.bean.TimeDataBean;
import com.jinke.housekeeper.saas.patrol.http.HttpMethods;
import com.jinke.housekeeper.saas.patrol.http.ProgressSubscriber;
import com.jinke.housekeeper.saas.patrol.http.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.patrol.listener.ContrastRequestListener;
import com.jinke.housekeeper.saas.patrol.listener.OnRequestListener;
import com.jinke.housekeeper.saas.patrol.service.PatrolStatementBiz;

import java.util.List;
import java.util.Map;

/**
 * author : huominghao
 * date : 2018/3/4 0004
 * function :
 */

public class PatrolStatementBizIml implements PatrolStatementBiz {

    private Context context;

    public PatrolStatementBizIml(Context context) {
        this.context = context;
    }

    @Override
    public void pointProjectData(Map<String, String> map, final OnRequestListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<List<PointProjectDataBean>>() {
            @Override
            public void onNext(List<PointProjectDataBean> bean) {
                listener.onSuccess(bean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().pointProjectData(new ProgressSubscriber<HttpResult<List<PointProjectDataBean>>>(onNextListener, context), map);
    }

    @Override
    public void contrastData(Map<String, String> map, final ContrastRequestListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<List<ContrastDataBean>>() {
            @Override
            public void onNext(List<ContrastDataBean> bean) {
                listener.onContrastSuccess(bean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().contrastData(new ProgressSubscriber<HttpResult<List<ContrastDataBean>>>(onNextListener, context), map);
    }
}

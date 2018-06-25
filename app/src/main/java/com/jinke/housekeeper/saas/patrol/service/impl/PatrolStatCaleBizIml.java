package com.jinke.housekeeper.saas.patrol.service.impl;

import android.content.Context;

import com.jinke.housekeeper.saas.patrol.bean.HttpResult;
import com.jinke.housekeeper.saas.patrol.bean.PointBean;
import com.jinke.housekeeper.saas.patrol.bean.TimeDataBean;
import com.jinke.housekeeper.saas.patrol.http.HttpMethods;
import com.jinke.housekeeper.saas.patrol.http.ProgressSubscriber;
import com.jinke.housekeeper.saas.patrol.http.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.patrol.listener.OnRequestListener;
import com.jinke.housekeeper.saas.patrol.service.PatrolStatCaleBiz;

import java.util.List;
import java.util.Map;

/**
 * author : huominghao
 * date : 2018/3/4 0004
 * function :
 */

public class PatrolStatCaleBizIml implements PatrolStatCaleBiz {

    private Context context;

    public PatrolStatCaleBizIml(Context context) {
        this.context = context;
    }

    @Override
    public void timeData(Map<String, String> map, final OnRequestListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<List<TimeDataBean>>() {
            @Override
            public void onNext(List<TimeDataBean> bean) {
                listener.onSuccess(bean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().timeData(new ProgressSubscriber<HttpResult<List<TimeDataBean>>>(onNextListener, context), map);
    }
}

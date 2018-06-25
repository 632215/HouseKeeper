package com.jinke.housekeeper.saas.patrol.service.impl;

import android.content.Context;

import com.jinke.housekeeper.saas.patrol.bean.HttpResult;
import com.jinke.housekeeper.saas.patrol.bean.IsStartBean;
import com.jinke.housekeeper.saas.patrol.bean.PointDataBean;
import com.jinke.housekeeper.saas.patrol.bean.PointPlanBean;
import com.jinke.housekeeper.saas.patrol.http.HttpMethods;
import com.jinke.housekeeper.saas.patrol.listener.OnRequestListener;
import com.jinke.housekeeper.saas.patrol.http.ProgressSubscriber;
import com.jinke.housekeeper.saas.patrol.http.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.patrol.listener.PointPlanListener;
import com.jinke.housekeeper.saas.patrol.service.PointPlanBiz;

import java.util.Map;

/**
 * author : huominghao
 * date : 2018/1/25 0025
 * function :
 */

public class PointPlanBizIml implements PointPlanBiz {

    private Context mContext;

    public PointPlanBizIml(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getTaskInfo(Map<String, String> map, final OnRequestListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<PointPlanBean>() {
            @Override
            public void onNext(PointPlanBean bean) {
                listener.onSuccess(bean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().getTaskInfo(new ProgressSubscriber<HttpResult<PointPlanBean>>(onNextListener, mContext), map);
    }


    @Override
    public void isStart(Map<String, String> map, final PointPlanListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<IsStartBean>() {
            @Override
            public void onNext(IsStartBean bean) {
                listener.onPointPlanList(bean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().isStart(new ProgressSubscriber<HttpResult<IsStartBean>>(onNextListener, mContext), map);

    }
}

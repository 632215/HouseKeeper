package com.jinke.housekeeper.saas.patrol.service.impl;

import android.content.Context;

import com.jinke.housekeeper.saas.patrol.bean.EmptyBean;
import com.jinke.housekeeper.saas.patrol.bean.HttpResult;
import com.jinke.housekeeper.saas.patrol.http.HttpMethods;
import com.jinke.housekeeper.saas.patrol.http.ProgressSubscriber;
import com.jinke.housekeeper.saas.patrol.http.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.patrol.listener.IAddPointListener;
import com.jinke.housekeeper.saas.patrol.service.AddPatrolBiz;

import java.util.Map;

/**
 * Created by root on 17-7-24.
 */

public class AddPatrolBizImpl implements AddPatrolBiz {

    private Context mContext;

    public AddPatrolBizImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void addPoint(Map<String, String> map, final IAddPointListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<EmptyBean>() {
            @Override
            public void onNext(EmptyBean bean) {
                listener.onBackAddPoint(bean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().addPoint(new ProgressSubscriber<HttpResult<EmptyBean>>(onNextListener, mContext), map);
    }
}

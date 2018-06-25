package com.jinke.housekeeper.saas.patrol.service.impl;

import android.content.Context;

import com.jinke.housekeeper.saas.patrol.bean.HttpResult;
import com.jinke.housekeeper.saas.patrol.bean.SignPatrolListBean;
import com.jinke.housekeeper.saas.patrol.http.HttpMethods;
import com.jinke.housekeeper.saas.patrol.listener.OnRequestListener;
import com.jinke.housekeeper.saas.patrol.http.ProgressSubscriber;
import com.jinke.housekeeper.saas.patrol.http.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.patrol.service.PatrolBeganBiz;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/7/28
 */

public class PatrolBeganBizImpl implements PatrolBeganBiz {

    private Context mContext;

    public PatrolBeganBizImpl(Context mContext) {
        this.mContext = mContext;

    }

    @Override
    public void getIfSignOut(Map<String, String> map, final OnRequestListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<SignPatrolListBean>() {
            @Override
            public void onNext(SignPatrolListBean bean) {
                listener.onSuccess(bean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getIfSignOut(new ProgressSubscriber<HttpResult<SignPatrolListBean>>(onNextListener, mContext), map);
    }
}

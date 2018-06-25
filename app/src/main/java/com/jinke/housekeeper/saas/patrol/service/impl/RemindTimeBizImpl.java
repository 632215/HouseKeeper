package com.jinke.housekeeper.saas.patrol.service.impl;

import android.content.Context;

import com.jinke.housekeeper.saas.patrol.bean.HttpResult;
import com.jinke.housekeeper.saas.patrol.bean.RemindTimeBean;
import com.jinke.housekeeper.saas.patrol.http.HttpMethods;
import com.jinke.housekeeper.saas.patrol.listener.OnRequestListener;
import com.jinke.housekeeper.saas.patrol.http.ProgressSubscriber;
import com.jinke.housekeeper.saas.patrol.http.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.patrol.service.RemindTimeBiz;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/8/3
 */

public class RemindTimeBizImpl implements RemindTimeBiz {

    private Context mContext;

    public RemindTimeBizImpl(Context mContext) {
        this.mContext = mContext;

    }

    @Override
    public void getRemindTime(Map<String, String> map, final OnRequestListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<RemindTimeBean>() {
            @Override
            public void onNext(RemindTimeBean bean) {
                listener.onSuccess(bean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getRemindTime(new ProgressSubscriber<HttpResult<RemindTimeBean>>(onNextListener, mContext), map);
    }

}

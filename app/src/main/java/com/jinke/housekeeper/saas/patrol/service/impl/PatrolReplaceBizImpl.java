package com.jinke.housekeeper.saas.patrol.service.impl;

import android.content.Context;

import com.jinke.housekeeper.saas.patrol.bean.EmptyBean;
import com.jinke.housekeeper.saas.patrol.bean.HttpResult;
import com.jinke.housekeeper.saas.patrol.bean.SystemTimeBean;
import com.jinke.housekeeper.saas.patrol.http.HttpMethods;
import com.jinke.housekeeper.saas.patrol.http.ProgressSubscriber;
import com.jinke.housekeeper.saas.patrol.http.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.patrol.listener.PatrolReplaceListener;
import com.jinke.housekeeper.saas.patrol.service.PatrolReplaceBiz;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/11/13
 */

public class PatrolReplaceBizImpl implements PatrolReplaceBiz {

    private Context mContext;

    public PatrolReplaceBizImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void patrolReplace(Map<String, String> map, final PatrolReplaceListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<EmptyBean>() {
            @Override
            public void onNext(EmptyBean bean) {
                listener.onBackPatrolReplace(bean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().replacePoint(new ProgressSubscriber<HttpResult<EmptyBean>>(onNextListener, mContext), map);
    }
}

package com.jinke.housekeeper.service.impl;

import android.content.Context;

import com.jinke.housekeeper.bean.TestInfo;
import com.jinke.housekeeper.http.HttpMethods;
import com.jinke.housekeeper.http.HttpResult;
import com.jinke.housekeeper.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.service.biz.RegisterActivityBiz;
import com.jinke.housekeeper.service.listener.RegisterActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/15.
 */

public class RegisterActivityImpl implements RegisterActivityBiz {
    private Context context;

    public RegisterActivityImpl(Context context ) {
        this.context = context;
    }

    @Override
    public void getRegisterData(SortedMap<String, String> map, final RegisterActivityListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<TestInfo>() {
            @Override
            public void onNext(TestInfo info) {
                if (info != null) {
                 listener.getRegisterDataonNext(info);
                }
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getRegisterDataonError( Code,  Msg);

            }
        };

        HttpMethods.getInstance().getRegisterData(new ProgressSubscriber<HttpResult<TestInfo>>(onNextListener, context, true), map);
    }
}

package com.jinke.housekeeper.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.bean.LoginInfo;
import com.jinke.housekeeper.http.HttpMethods;
import com.jinke.housekeeper.http.HttpResult;
import com.jinke.housekeeper.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.service.biz.LoginActivityBiz;
import com.jinke.housekeeper.service.listener.LoginActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/15.
 */

public class LoginActivityImpl implements LoginActivityBiz {
    private Context context;

    public LoginActivityImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getUserLoginData(SortedMap<String, String> map, final LoginActivityListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<LoginInfo>() {
            @Override
            public void onNext(LoginInfo info) {
                if (info != null) {
                   listener.getUserLoginDataonNext(info);
                }
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getUserLoginDataonError(Code,Msg);
            }
        };
        HttpMethods.getInstance().getUserLoginData(new ProgressSubscriber<HttpResult<LoginInfo>>(onNextListener, context, true), map, MyApplicaption.createSign(map));
    }
}

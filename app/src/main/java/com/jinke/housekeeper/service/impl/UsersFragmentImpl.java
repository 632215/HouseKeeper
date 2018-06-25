package com.jinke.housekeeper.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.bean.AppHandleInfo;
import com.jinke.housekeeper.bean.UserPushBean;
import com.jinke.housekeeper.http.HttpMethods;
import com.jinke.housekeeper.http.HttpResult;
import com.jinke.housekeeper.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.service.listener.UsersFragmentListener;
import com.jinke.housekeeper.service.biz.UsersFragmentBiz;
import com.jinke.housekeeper.service.listener.userPushListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/12.
 */

public class UsersFragmentImpl implements UsersFragmentBiz {
    private Context context;
    public UsersFragmentImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getLoginOut(SortedMap<String, String> map, final UsersFragmentListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<AppHandleInfo>() {
            @Override
            public void onNext(AppHandleInfo info) {
                if (info != null) {
                    listener.getLoinOutonNext(info);}
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getLoinOutonError(Code,  Msg);

            }
        };
        HttpMethods.getInstance().getuserLoginOut(new ProgressSubscriber<HttpResult<AppHandleInfo>>(onNextListener,context, true), map, MyApplicaption.createSign(map));

    }

    @Override
    public void userPush(SortedMap<String, String> map, final userPushListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<UserPushBean>() {
            @Override
            public void onNext(UserPushBean info) {
                if (info != null) {
                    listener.userPushNext(info);}
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.userPushError(Code,  Msg);

            }
        };
        HttpMethods.getInstance().userPush(new ProgressSubscriber<HttpResult<UserPushBean>>(onNextListener,context, true), map, MyApplicaption.createSign(map));
    }
}

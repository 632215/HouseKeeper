package com.jinke.housekeeper.saas.patrol.service.impl;

import android.content.Context;

import com.jinke.housekeeper.saas.patrol.bean.EmptyBean;
import com.jinke.housekeeper.saas.patrol.bean.HttpResult;
import com.jinke.housekeeper.saas.patrol.bean.PatrolRecordBean;
import com.jinke.housekeeper.saas.patrol.bean.PointBean;
import com.jinke.housekeeper.saas.patrol.bean.SignPatrolBean;
import com.jinke.housekeeper.saas.patrol.bean.SystemTimeBean;
import com.jinke.housekeeper.saas.patrol.http.HttpMethods;
import com.jinke.housekeeper.saas.patrol.http.ProgressSubscriber;
import com.jinke.housekeeper.saas.patrol.http.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.patrol.listener.IPatrolRecordListener;
import com.jinke.housekeeper.saas.patrol.listener.IPunchCardListener;
import com.jinke.housekeeper.saas.patrol.listener.ISignInListener;
import com.jinke.housekeeper.saas.patrol.listener.ISignOutListener;
import com.jinke.housekeeper.saas.patrol.listener.ISystemTimeListener;
import com.jinke.housekeeper.saas.patrol.listener.PointListListener;
import com.jinke.housekeeper.saas.patrol.service.PatrolSignInBiz;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/7/25
 */

public class PatrolSignInBizIml implements PatrolSignInBiz {
    private Context mContext;

    public PatrolSignInBizIml(Context mContext) {
        this.mContext = mContext;
    }
    @Override
    public void getSystemTime(Map<String, String> map, final ISystemTimeListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<SystemTimeBean>() {
            @Override
            public void onNext(SystemTimeBean bean) {
                listener.onBackSystemTime(bean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().getSystemTime(new ProgressSubscriber<HttpResult<SystemTimeBean>>(onNextListener, mContext), map);
    }

    @Override
    public void signPatrol(Map<String, String> map, final ISignInListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<SignPatrolBean>() {
            @Override
            public void onNext(SignPatrolBean bean) {
                listener.onSignIn(bean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().signPatrol(new ProgressSubscriber<HttpResult<SignPatrolBean>>(onNextListener, mContext), map);
    }

    @Override
    public void signOutPatrol(Map<String, String> map, final ISignOutListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<EmptyBean>() {
            @Override
            public void onNext(EmptyBean bean) {
                listener.onSignOut();
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().signOutPatrol(new ProgressSubscriber<HttpResult<EmptyBean>>(onNextListener, mContext), map);
    }

    @Override
    public void patrolPunchCard(Map<String, String> map, final IPunchCardListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<EmptyBean>() {
            @Override
            public void onNext(EmptyBean bean) {
                listener.onBackPunchCard();
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().patrolPunchCard(new ProgressSubscriber<HttpResult<EmptyBean>>(onNextListener, mContext), map);
    }

    @Override
    public void getPatrolRecord(Map<String, String> map, final IPatrolRecordListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<PatrolRecordBean>() {
            @Override
            public void onNext(PatrolRecordBean bean) {
                listener.onBackPatrolRecord(bean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getPatrolRecord(new ProgressSubscriber<HttpResult<PatrolRecordBean>>(onNextListener, mContext), map);
    }

    @Override
    public void getPointList(Map<String, String> map, final PointListListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<PointBean>() {
            @Override
            public void onNext(PointBean bean) {
                listener.onBackPointList(bean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getPointList(new ProgressSubscriber<HttpResult<PointBean>>(onNextListener, mContext), map);
    }

}

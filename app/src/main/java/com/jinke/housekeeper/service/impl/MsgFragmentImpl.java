package com.jinke.housekeeper.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.http.HttpMethods;
import com.jinke.housekeeper.http.HttpResult;
import com.jinke.housekeeper.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.bean.EmptyBean;
import com.jinke.housekeeper.bean.MsgBean;
import com.jinke.housekeeper.bean.PersonalTaskBean;
import com.jinke.housekeeper.service.listener.MsgFragmentListener;
import com.jinke.housekeeper.service.biz.MsgFragmentBiz;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/6.
 */

public class MsgFragmentImpl implements MsgFragmentBiz {
    private Context mContext;

    public MsgFragmentImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void requestMsgList(SortedMap<String, String> map, final MsgFragmentListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<MsgBean>() {
            @Override
            public void onNext(MsgBean info) {
                listener.requestMsgListNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.requestMsgListError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getMsgList(new ProgressSubscriber<HttpResult<MsgBean>>(onNextListener, mContext, true), map, MyApplicaption.createSign(map));
    }

    @Override
    public void requestMsgData(SortedMap<String, String> map, final MsgFragmentListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<PersonalTaskBean>() {
            @Override
            public void onNext(final PersonalTaskBean info) {
                listener.requestMsgDataNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.requestMsgDataError(Code, Msg);
            }
        };
        HttpMethods.getInstance().personalTask(new ProgressSubscriber<HttpResult<PersonalTaskBean>>(onNextListener, mContext, false), map, MyApplicaption.createSign(map));
    }

    @Override
    public void updateReadStatus(SortedMap<String, String> map, final MsgFragmentListener listener, final int p) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<EmptyBean>() {
            @Override
            public void onNext(EmptyBean info) {
                listener.updateReadStatusNext(p);
            }

            @Override
            public void onError(String Code, String Msg) {
            }
        };

        HttpMethods.getInstance().updateReadStatus(new ProgressSubscriber<HttpResult<EmptyBean>>(onNextListener, mContext, false), map, MyApplicaption.createSign(map));
    }
}

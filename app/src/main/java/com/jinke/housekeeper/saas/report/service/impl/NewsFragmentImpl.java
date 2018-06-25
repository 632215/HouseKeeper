package com.jinke.housekeeper.saas.report.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.bean.EmptyBean;
import com.jinke.housekeeper.bean.MsgBean;
import com.jinke.housekeeper.saas.report.http.HttpMethods;
import com.jinke.housekeeper.saas.report.http.HttpResult;
import com.jinke.housekeeper.saas.report.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.saas.report.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.report.service.NewsFragmentBiz;
import com.jinke.housekeeper.saas.report.service.listener.NewsFragmentListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/7.
 */

public class NewsFragmentImpl implements NewsFragmentBiz {
    private Context context;

    public NewsFragmentImpl(Context context) {
        this.context = context;
    }

    @Override
    public void initMagData(SortedMap<String, String> map, final NewsFragmentListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<MsgBean>() {
            @Override
            public void onNext(MsgBean info) {
                listener.initMagDataNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.initMagDataError(Code,  Msg);

            }
        };
        HttpMethods.getInstance().getMsgList(new ProgressSubscriber<HttpResult<MsgBean>>(onNextListener, context, true), map, MyApplicaption.createSign(map));
    }

    @Override
    public void updateReadStatus(SortedMap<String, String> map, final NewsFragmentListener listener, final int p) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<EmptyBean>() {
            @Override
            public void onNext(EmptyBean info) {
                listener.updateReadStatusNext(info,p);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.updateReadStatusError(Code,  Msg);
            }
        };
        HttpMethods.getInstance().updateReadStatus(new ProgressSubscriber<HttpResult<EmptyBean>>(onNextListener, context, true), map, MyApplicaption.createSign(map));
    }
}

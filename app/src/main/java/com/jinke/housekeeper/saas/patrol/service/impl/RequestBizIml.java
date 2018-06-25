package com.jinke.housekeeper.saas.patrol.service.impl;


import android.content.Context;

import com.jinke.housekeeper.saas.patrol.bean.HttpResult;
import com.jinke.housekeeper.saas.patrol.bean.PointBean;
import com.jinke.housekeeper.saas.patrol.bean.TokenBean;
import com.jinke.housekeeper.saas.patrol.http.HttpMethods;
import com.jinke.housekeeper.saas.patrol.listener.OnRequestListener;
import com.jinke.housekeeper.saas.patrol.http.ProgressSubscriber;
import com.jinke.housekeeper.saas.patrol.http.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.patrol.listener.IAddPointListener;
import com.jinke.housekeeper.saas.patrol.service.RequestBiz;

import java.util.Map;

public class RequestBizIml implements RequestBiz {

    private Context mContext;

    public RequestBizIml(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void requestForData(Map<String, String> map, final OnRequestListener listener) {

        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<TokenBean>() {
            @Override
            public void onNext(TokenBean bean) {
                listener.onSuccess(bean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().getToken(new ProgressSubscriber<HttpResult<TokenBean>>(onNextListener, mContext), map);
    }

    @Override
    public void getPointList(Map<String, String> map, final IAddPointListener.OnGetPoinListListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<PointBean>() {
            @Override
            public void onNext(PointBean bean) {
                listener.OnGetPoinListSuccess(bean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getPointList(new ProgressSubscriber<HttpResult<PointBean>>(onNextListener, mContext), map);
    }


//    @Override
//    public void getPlatformOpenID(Map<String, String> map, final IOpenIdListener listener) {
//
//        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<List<LoginBean>>() {
//            @Override
//            public void onNext(List<LoginBean> bean) {
//                bean.add(new LoginBean());
//                listener.onBackOpenID();
//            }
//
//            @Override
//            public void onError(String Code, String Msg) {
//                listener.onError(Code, Msg);
//            }
//        };
//        PlatformMethods.getInstance().getPlatformOauth(new ProgressSubscriber<HttpResult<LoginBean>>(onNextListener, mContext), map);
//    }
}
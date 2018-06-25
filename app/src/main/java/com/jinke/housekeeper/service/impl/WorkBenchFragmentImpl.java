package com.jinke.housekeeper.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.bean.OpenIdBean;
import com.jinke.housekeeper.http.HttpMethods;
import com.jinke.housekeeper.http.HttpResult;
import com.jinke.housekeeper.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.report.service.WorkBenchFragmentBiz;
import com.jinke.housekeeper.service.listener.WorkBenchFragmentListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/6.
 */

public class WorkBenchFragmentImpl implements WorkBenchFragmentBiz {
    private Context mContext;

    public WorkBenchFragmentImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getMapPoint(SortedMap<String, String> map, final WorkBenchFragmentListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<OpenIdBean>() {
            @Override
            public void onNext(OpenIdBean info) {
                if (info != null) {
                    listener.getMapPointNext(info);
                }
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getMapPointError(Code, Msg);
            }
        };
        HttpMethods.getInstance().platformOauth(new ProgressSubscriber<HttpResult<OpenIdBean>>(onNextListener, mContext, true), map, MyApplicaption.createSign(map));
    }
}

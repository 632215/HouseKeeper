package com.jinke.housekeeper.saas.report.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.bean.AppHandleInfo;
import com.jinke.housekeeper.saas.report.bean.WaitRectifiedBean;
import com.jinke.housekeeper.saas.report.config.ReportConfig;
import com.jinke.housekeeper.saas.report.http.HttpMethods;
import com.jinke.housekeeper.saas.report.http.HttpResult;
import com.jinke.housekeeper.saas.report.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.saas.report.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.report.service.listener.WaitRectifiedFragmentListener;
import com.jinke.housekeeper.service.biz.WaitRectifiedFragmentBiz;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class WaitRectifiedFragmentImpl implements WaitRectifiedFragmentBiz {
    private Context context;

    public WaitRectifiedFragmentImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getWaitList(SortedMap<String, String> map, final WaitRectifiedFragmentListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<WaitRectifiedBean>() {
            @Override
            public void onNext(WaitRectifiedBean WaitRectifiedBean) {
               listener.getWaitListonNext(WaitRectifiedBean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getWaitListonError(Code,  Msg);

            }
        };
        HttpMethods.getInstance().getWaitRectified(new ProgressSubscriber<HttpResult<WaitRectifiedBean>>(onNextListener, context, false), map, ReportConfig.createSign(map));
    }

    @Override
    public void getAppTaskSignData(SortedMap<String, String> map, final WaitRectifiedFragmentListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<AppHandleInfo>() {
            @Override
            public void onNext(AppHandleInfo info) {
                if (info != null) {
                }
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getAppTaskSignDataonError(Code,  Msg);

            }
        };

        HttpMethods.getInstance().getAppTaskSignData(new ProgressSubscriber<HttpResult<AppHandleInfo>>(onNextListener, context, true), map, MyApplicaption.createSign(map));
    }
}

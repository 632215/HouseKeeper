package com.jinke.housekeeper.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.bean.CountXMListInfo;
import com.jinke.housekeeper.http.HttpMethods;
import com.jinke.housekeeper.http.HttpResult;
import com.jinke.housekeeper.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.service.listener.ProjectSelectionActivityListener;
import com.jinke.housekeeper.service.biz.ProjectSelectionActivityBiz;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/7.
 */

public class ProjectSelectionActivityImpl implements ProjectSelectionActivityBiz {
    private Context context;

    public ProjectSelectionActivityImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getCountXMList(SortedMap<String, String> map, final ProjectSelectionActivityListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<CountXMListInfo>() {
            @Override
            public void onNext(CountXMListInfo info) {
    listener.getCountXMListonNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getCountXMListonErrort(Code,Msg);
            }
        };
        HttpMethods.getInstance().getCountXMList(new ProgressSubscriber<HttpResult<CountXMListInfo>>(onNextListener,context, true), map, MyApplicaption.createSign(map));

    }
}

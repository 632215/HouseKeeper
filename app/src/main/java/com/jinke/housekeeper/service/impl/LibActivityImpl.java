package com.jinke.housekeeper.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.bean.KnowledgeInfo;
import com.jinke.housekeeper.http.HttpMethods;
import com.jinke.housekeeper.http.HttpResult;
import com.jinke.housekeeper.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.service.listener.LibActivityListener;
import com.jinke.housekeeper.service.biz.LibActivityBiz;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class LibActivityImpl implements LibActivityBiz {
    private Context context;

    public LibActivityImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getLore(SortedMap<String, String> map, final LibActivityListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<KnowledgeInfo>() {
            @Override
            public void onNext(KnowledgeInfo info) {
                if (info != null) {
listener.getLoreonNext(info);
                }
            }

            @Override
            public void onError(String Code, String Msg) {
listener.getLoreonError(Code,Msg);
            }
        };

        HttpMethods.getInstance().getLoreData(new ProgressSubscriber<HttpResult<KnowledgeInfo>>(onNextListener, context, false), map, MyApplicaption.createSign(map));
    }
}

package com.jinke.housekeeper.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.bean.LibAllInfo;
import com.jinke.housekeeper.http.HttpMethods;
import com.jinke.housekeeper.http.HttpResult;
import com.jinke.housekeeper.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.service.listener.LibAllActivityListener;
import com.jinke.housekeeper.service.biz.LibAllActivityBiz;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class LibAllActivityImpl implements LibAllActivityBiz {
    private Context context;

    public LibAllActivityImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getScenePage(SortedMap<String, String> map, final LibAllActivityListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<LibAllInfo>() {
            @Override
            public void onNext(LibAllInfo info) {
                listener.getScenePageonNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getScenePageonError( Code,  Msg);

            }
        };
        HttpMethods.getInstance().getScenePageData(new ProgressSubscriber<HttpResult<LibAllInfo>>(onNextListener, context, false), map, MyApplicaption.createSign(map));
    }
}

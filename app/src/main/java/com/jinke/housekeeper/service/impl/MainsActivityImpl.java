package com.jinke.housekeeper.service.impl;

import android.content.Context;

import com.jinke.housekeeper.bean.ScenePageInfo;
import com.jinke.housekeeper.http.HttpMethods;
import com.jinke.housekeeper.http.HttpResult;
import com.jinke.housekeeper.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.service.biz.MainsActivityBiz;
import com.jinke.housekeeper.service.listener.MainsActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/15.
 */

public class MainsActivityImpl implements MainsActivityBiz {
    private Context context;

    public MainsActivityImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getAllScenePage(SortedMap<String, String> map, final MainsActivityListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<ScenePageInfo>() {
            @Override
            public void onNext(ScenePageInfo info) {
                listener.getAllScenePageonNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getAllScenePageonError(Code,  Msg);

            }
        };
        HttpMethods.getInstance().getAllScenePage(new ProgressSubscriber<HttpResult<ScenePageInfo>>(onNextListener, context, false), map);
    }
}

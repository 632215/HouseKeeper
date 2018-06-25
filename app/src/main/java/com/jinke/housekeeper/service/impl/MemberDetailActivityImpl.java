package com.jinke.housekeeper.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.http.HttpMethods;
import com.jinke.housekeeper.http.HttpResult;
import com.jinke.housekeeper.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.service.listener.MemberDetailActivityListener;
import com.jinke.housekeeper.service.biz.MemberDetailActivityBiz;
import com.jinke.housekeeper.bean.TestInfo;
import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/7.
 */

public class MemberDetailActivityImpl implements MemberDetailActivityBiz {
    private Context context;

    public MemberDetailActivityImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getUserDelete(SortedMap<String, String> map, final MemberDetailActivityListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<TestInfo>() {
            @Override
            public void onNext(TestInfo testInfo) {
                if (testInfo != null) {
                   listener.getUserDeleteNext(testInfo);
                }
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getUserDeleteError( Code,  Msg);

            }
        };

        HttpMethods.getInstance()
                .getUserPass(new ProgressSubscriber<HttpResult<TestInfo>>(onNextListener, context, false), map, MyApplicaption.createSign(map));
    }
}

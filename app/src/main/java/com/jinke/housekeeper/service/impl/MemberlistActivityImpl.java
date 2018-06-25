package com.jinke.housekeeper.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.bean.MemberListBean;
import com.jinke.housekeeper.http.HttpMethods;
import com.jinke.housekeeper.http.HttpResult;
import com.jinke.housekeeper.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.service.listener.MemberlistActivityListener;
import com.jinke.housekeeper.service.biz.MemberlistActivityBiz;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/7.
 */

public class MemberlistActivityImpl implements MemberlistActivityBiz {
    private Context context;

    public MemberlistActivityImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getMenberList(SortedMap<String, String> map, final MemberlistActivityListener listener, boolean isShow) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<MemberListBean>() {
            @Override
            public void onNext(MemberListBean dataBean) {
                listener.getMenberListNext(dataBean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getMenberListError( Code,  Msg);
            }
        };

        HttpMethods.getInstance().getMenberList(new ProgressSubscriber<HttpResult<MemberListBean>>(onNextListener, context, isShow), map, MyApplicaption.createSign(map));

    }
}

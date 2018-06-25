package com.jinke.housekeeper.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.bean.LibDetailsInfo;
import com.jinke.housekeeper.service.listener.LibDetailsActivityListener;
import com.jinke.housekeeper.service.biz.LibDetailsActivityBiz;
import com.jinke.housekeeper.http.HttpMethods;
import com.jinke.housekeeper.http.HttpResult;
import com.jinke.housekeeper.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.http.progress.SubscriberOnNextListener;
import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class LibDetailsActivityImpl implements LibDetailsActivityBiz {
    private Context context;

    public LibDetailsActivityImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getDetailsData(SortedMap<String, String> map, final LibDetailsActivityListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<LibDetailsInfo>() {
            @Override
            public void onNext(LibDetailsInfo info) {
                if (info != null) {
                    listener.getDetailsDataonNext(info);
                }
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getDetailsDataonError(Code,  Msg);

            }
        };

        HttpMethods.getInstance().getKeyId(new ProgressSubscriber<HttpResult<LibDetailsInfo>>(onNextListener, context, false), map, MyApplicaption.createSign(map));

    }
}

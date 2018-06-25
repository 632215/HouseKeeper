package com.jinke.housekeeper.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.bean.MailListBean;
import com.jinke.housekeeper.http.HttpMethods;
import com.jinke.housekeeper.http.HttpResult;
import com.jinke.housekeeper.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.service.biz.ContactActivityBiz;
import com.jinke.housekeeper.service.listener.ContactActivityListener;

import java.util.SortedMap;

/**
 * Created by hank on 2017/12/18 0018.
 */

public class ContactActivityBizImpl implements ContactActivityBiz {

    private Context mContext;

    public ContactActivityBizImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getMailList(SortedMap<String, String> map, final ContactActivityListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<MailListBean>() {
            @Override
            public void onNext(MailListBean info) {
                listener.getMailListNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getMailListError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getMailList(new ProgressSubscriber<HttpResult<MailListBean>>(onNextListener, mContext, true), map, MyApplicaption.createSign(map));
    }
}

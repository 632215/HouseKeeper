package com.jinke.housekeeper.saas.report.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.bean.RegisterDepartmentBean;
import com.jinke.housekeeper.saas.report.config.ReportConfig;
import com.jinke.housekeeper.saas.report.http.HttpMethods;
import com.jinke.housekeeper.saas.report.http.HttpResult;
import com.jinke.housekeeper.saas.report.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.saas.report.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.report.service.ContactsActivityBiz;
import com.jinke.housekeeper.saas.report.service.listener.ContactsActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class ContactsActivityImpl implements ContactsActivityBiz {
    private Context context;

    public ContactsActivityImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getUserListData(SortedMap<String, String> map, final ContactsActivityListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<RegisterDepartmentBean>() {
            @Override
            public void onNext(RegisterDepartmentBean info) {
                listener.getUserListDataonNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getUserListDataonError(Code,  Msg);

            }
        };

        HttpMethods.getInstance().getselectUserBySceneData(new ProgressSubscriber<HttpResult<RegisterDepartmentBean>>(onNextListener, context, false), map, ReportConfig.createSign(map));

    }
}

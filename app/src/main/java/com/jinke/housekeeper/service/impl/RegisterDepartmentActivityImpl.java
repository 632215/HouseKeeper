package com.jinke.housekeeper.service.impl;

import android.content.Context;

import com.jinke.housekeeper.bean.RegisterDepartmentBean;
import com.jinke.housekeeper.saas.report.http.HttpMethods;
import com.jinke.housekeeper.saas.report.http.HttpResult;
import com.jinke.housekeeper.saas.report.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.saas.report.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.service.biz.RegisterDepartmentActivityBiz;
import com.jinke.housekeeper.service.listener.RegisterDepartmentActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/15.
 */

public class RegisterDepartmentActivityImpl implements RegisterDepartmentActivityBiz {
    private Context context;

    public RegisterDepartmentActivityImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getCJContentData(SortedMap<String, String> map, final RegisterDepartmentActivityListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<RegisterDepartmentBean>() {
            @Override
            public void onNext(RegisterDepartmentBean info) {
              listener.getCJContentDataOnNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getCJContentDataOnError(Code,  Msg);

            }
        };
        HttpMethods.getInstance().getCJContentData(new ProgressSubscriber<HttpResult<RegisterDepartmentBean>>(onNextListener, context, false), map);
    }
}

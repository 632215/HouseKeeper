package com.jinke.housekeeper.service.impl;

import android.content.Context;

import com.jinke.housekeeper.bean.RegisterDepartmentBean;
import com.jinke.housekeeper.http.HttpMethods;
import com.jinke.housekeeper.http.HttpResult;
import com.jinke.housekeeper.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.service.listener.DepartmentSelectActivityListener;
import com.jinke.housekeeper.service.biz.DepartmentSelectActivityBiz;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/7.
 */

public class DepartmentSelectActivityImpl implements DepartmentSelectActivityBiz {
    private Context context;

    public DepartmentSelectActivityImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getCJContentData(SortedMap<String, String> map, final DepartmentSelectActivityListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<RegisterDepartmentBean>() {
            @Override
            public void onNext(RegisterDepartmentBean info) {
                listener.getCJContentDataonNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getCJContentDataonError(Code,  Msg);

            }
        };
        HttpMethods.getInstance().getCJContentData(new ProgressSubscriber<HttpResult<RegisterDepartmentBean>>(onNextListener, context, false), map);
    }
}

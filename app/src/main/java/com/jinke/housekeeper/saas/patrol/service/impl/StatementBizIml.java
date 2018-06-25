package com.jinke.housekeeper.saas.patrol.service.impl;

import android.content.Context;

import com.jinke.housekeeper.saas.patrol.bean.HttpResult;
import com.jinke.housekeeper.saas.patrol.bean.PointDataBean;
import com.jinke.housekeeper.saas.patrol.http.HttpMethods;
import com.jinke.housekeeper.saas.patrol.http.ProgressSubscriber;
import com.jinke.housekeeper.saas.patrol.http.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.patrol.listener.OnRequestListener;
import com.jinke.housekeeper.saas.patrol.service.StatementBiz;

import java.util.List;
import java.util.Map;

/**
 * author : huominghao
 * date : 2018/3/2 0002
 * function :
 */

public class StatementBizIml implements StatementBiz {

    private Context mContext;

    public StatementBizIml(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void pointData(Map<String, String> map, final OnRequestListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<List<PointDataBean>>() {
            @Override
            public void onNext(List<PointDataBean> bean) {
                listener.onSuccess(bean.get(0));
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().pointData(new ProgressSubscriber<HttpResult<List<PointDataBean>>>(onNextListener, mContext), map);
    }

}

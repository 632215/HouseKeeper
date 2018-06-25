package com.jinke.housekeeper.saas.report.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.saas.report.bean.MapPointBean;
import com.jinke.housekeeper.saas.report.config.ReportConfig;
import com.jinke.housekeeper.saas.report.http.HttpMethods;
import com.jinke.housekeeper.saas.report.http.HttpResult;
import com.jinke.housekeeper.saas.report.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.saas.report.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.report.service.MapActivityBiz;
import com.jinke.housekeeper.saas.report.service.listener.MapActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class MapActivityImpl implements MapActivityBiz {
    private Context context;

    public MapActivityImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getMapPoint(SortedMap<String, String> map, final MapActivityListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<MapPointBean>() {
            @Override
            public void onNext(MapPointBean info) {
                if (info != null) {
                    listener.getMapPointOnNext(info);
                }
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getMapPointOnError(Code,Msg);
            }
        };

        HttpMethods.getInstance().getMapPoint(new ProgressSubscriber<HttpResult<MapPointBean>>(onNextListener, context, false), map, ReportConfig.createSign(map));

    }
}

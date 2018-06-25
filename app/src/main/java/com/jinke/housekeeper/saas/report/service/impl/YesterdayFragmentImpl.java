package com.jinke.housekeeper.saas.report.service.impl;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.saas.report.bean.RectifiedBean;
import com.jinke.housekeeper.saas.report.config.ReportConfig;
import com.jinke.housekeeper.saas.report.http.HttpMethods;
import com.jinke.housekeeper.saas.report.http.HttpResult;
import com.jinke.housekeeper.saas.report.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.saas.report.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.report.service.YesterdayFragmentBiz;
import com.jinke.housekeeper.saas.report.service.listener.YesterdayFragmentListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class YesterdayFragmentImpl implements YesterdayFragmentBiz {
    private Context context;

    public YesterdayFragmentImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getRectifiedList(SortedMap<String, String> map, final YesterdayFragmentListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<RectifiedBean>() {
            @Override
            public void onNext(RectifiedBean bean) {
                listener.getRectifiedListonNext(bean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getRectifiedListonError( Code,  Msg);

            }
        };
        HttpMethods.getInstance().getRectified(new ProgressSubscriber<HttpResult<RectifiedBean>>(onNextListener,context, false), map, ReportConfig.createSign(map));
    }
}

package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.bean.TodayChangeInfo;
import com.jinke.housekeeper.saas.report.bean.TodayWorkInfo;
import com.jinke.housekeeper.saas.report.service.QualityInspectionActivityBiz;
import com.jinke.housekeeper.saas.report.service.impl.QualityInspectionActivityImpl;
import com.jinke.housekeeper.saas.report.service.listener.QualityInspectionActivityListener;
import com.jinke.housekeeper.saas.report.view.QualityInspectionActivityView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class QualityInspectionActivityPresenter extends BasePresenter<QualityInspectionActivityView> implements QualityInspectionActivityListener {
    private Context context;

    private QualityInspectionActivityBiz biz;

    public QualityInspectionActivityPresenter(Context context) {
        this.context = context;
        biz = new QualityInspectionActivityImpl(context);
    }

    public void getToDayWork(SortedMap<String, String> map) {
        biz.gettodayWork(map, this);
    }

    @Override
    public void getToDayWorkOnNext(TodayWorkInfo info) {
        if (mView != null)
            mView.getTodayWorkOnNext(info);
    }

    @Override
    public void getToDayWorkOnError(String code, String msg) {
        if (mView != null)
            mView.getToDayWorkOnError(code, msg);
    }

    public void getToDayChange(SortedMap<String, String> map) {
        biz.gettodayChange(map, this);
    }

    @Override
    public void getToDayChangeOnNext(TodayChangeInfo info) {
        if (mView != null)
            mView.getToDayChangeOnNext(info);
    }

    @Override
    public void getToDayChangeOnError(String code, String msg) {
        if (mView != null)
            mView.getToDayChangeOnError(code, msg);
    }
}

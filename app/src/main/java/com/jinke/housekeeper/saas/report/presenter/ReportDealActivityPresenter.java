package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.service.impl.ReportDealActivityImpl;
import com.jinke.housekeeper.saas.report.service.listener.ReportDealActivityListener;
import com.jinke.housekeeper.saas.report.view.ReportDealActivityView;
import com.jinke.housekeeper.saas.report.service.ReportDealActivityBiz;

/**
 * Created by Administrator on 2017/9/11.
 */

public class ReportDealActivityPresenter extends BasePresenter<ReportDealActivityView> implements ReportDealActivityListener {
    private Context context;
    ReportDealActivityBiz biz;

    public ReportDealActivityPresenter(Context context) {
        this.context = context;
        biz =new ReportDealActivityImpl(context);
    }
}

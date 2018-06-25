package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.service.ReportSearchActivityBiz;
import com.jinke.housekeeper.saas.report.service.impl.ReportSearchActivityImpl;
import com.jinke.housekeeper.saas.report.service.listener.ReportSearchActivityListener;
import com.jinke.housekeeper.saas.report.view.ReportSearchActivityView;

/**
 * Created by Administrator on 2017/9/11.
 */

public class ReportSearchActivityPresenter extends BasePresenter<ReportSearchActivityView> implements ReportSearchActivityListener {
    private Context context;
    private ReportSearchActivityBiz biz;

    public ReportSearchActivityPresenter(Context context) {
        this.context = context;
        biz = new ReportSearchActivityImpl(context);
    }
}

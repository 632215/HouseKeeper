package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.service.ReportCommitActivityBiz;
import com.jinke.housekeeper.saas.report.service.impl.ReportCommitActivityImpl;
import com.jinke.housekeeper.saas.report.service.listener.ReportCommitActivityListener;
import com.jinke.housekeeper.saas.report.view.ReportCommitActivityView;

/**
 * Created by Administrator on 2017/9/11.
 */

public class ReportCommitActivityPresenter extends BasePresenter<ReportCommitActivityView> implements ReportCommitActivityListener {
    private Context context;
    ReportCommitActivityBiz biz;

    public ReportCommitActivityPresenter(Context context) {
        this.context = context;
        biz =new ReportCommitActivityImpl(context);
    }
}

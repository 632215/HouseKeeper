package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.service.ReportFragmentBiz;
import com.jinke.housekeeper.saas.report.service.impl.ReportFragmentImpl;
import com.jinke.housekeeper.saas.report.service.listener.ReportFragmentListener;
import com.jinke.housekeeper.saas.report.view.ReportFragmentView;
import com.lidroid.xutils.http.RequestParams;

/**
 * Created by Administrator on 2017/9/19.
 */

public class ReportFragmentPresenter extends BasePresenter<ReportFragmentView> implements ReportFragmentListener {
    private Context mContext;
    private ReportFragmentBiz mBiz;

    public ReportFragmentPresenter(Context mContext) {
        this.mContext = mContext;
        mBiz = new ReportFragmentImpl(mContext);
    }

    public void getUpFile(RequestParams params) {
        mView.showLoading();
        mBiz.getUpFile(params,this);
    }

    @Override
    public void getUpFileonSuccess() {
        mView.hideLoading();
        mView.getUpFileonSuccess();
    }

    @Override
    public void getFileUponFailure(String errorMsg) {
        mView.hideLoading();
        mView.getUpFileonFailure(errorMsg);
    }
}

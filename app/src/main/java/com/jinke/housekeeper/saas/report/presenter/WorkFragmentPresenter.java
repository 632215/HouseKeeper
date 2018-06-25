package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.service.WorkFragmentBiz;
import com.jinke.housekeeper.saas.report.service.impl.WorkFragmentImpl;
import com.jinke.housekeeper.saas.report.service.listener.WorkFragmentListener;
import com.jinke.housekeeper.saas.report.view.WorkFragmentView;
import com.lidroid.xutils.http.RequestParams;

/**
 * Created by Administrator on 2017/9/20.
 */

public class WorkFragmentPresenter extends BasePresenter<WorkFragmentView> implements WorkFragmentListener {
    private Context mContext;
    private WorkFragmentBiz mBiz;

    public WorkFragmentPresenter(Context mContext) {
        this.mContext = mContext;
        mBiz = new WorkFragmentImpl(mContext);
    }

    public void getappHandleData(RequestParams params) {
        mView.showLoading();
        mBiz.getappHandleData(params,this);
    }

    @Override
    public void getappHandleDataSuccess() {
        mView.hideLoading();
        mView.getappHandleDataSuccess();
    }

    @Override
    public void getappHandleDataFailure(String errorMsg) {
        mView.hideLoading();
        mView.getappHandleDataFailure(errorMsg);
    }
}

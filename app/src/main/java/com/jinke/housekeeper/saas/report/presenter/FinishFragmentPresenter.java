package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.service.FinishFragmentBiz;
import com.jinke.housekeeper.saas.report.service.impl.FinishFragmentImpl;
import com.jinke.housekeeper.saas.report.service.listener.FinishFragmentListener;
import com.jinke.housekeeper.saas.report.view.FinishFragmentView;
import com.lidroid.xutils.http.RequestParams;

/**
 * Created by Administrator on 2017/9/19.
 */

public class FinishFragmentPresenter extends BasePresenter<FinishFragmentView> implements FinishFragmentListener {

    private Context mContext;
    private FinishFragmentBiz mBiz;
    public FinishFragmentPresenter(Context mContext) {
        this.mContext = mContext;
        mBiz = new FinishFragmentImpl(mContext);
    }

    public void getFileUp(RequestParams params) {
        mView.showLoading();
        mBiz.getFileUp(params,this);
    }

    @Override
    public void getFileUponFailure(String errorMsg) {
        mView.hideLoading();
        mView.getFileUponFailure(errorMsg);
    }

    @Override
    public void getFileUponSuccess() {
        mView.hideLoading();
        mView.getFileUponSuccess();
    }
}

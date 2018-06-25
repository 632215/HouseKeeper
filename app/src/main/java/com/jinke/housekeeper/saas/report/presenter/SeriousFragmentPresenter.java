package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.service.SeriousFragmentBiz;
import com.jinke.housekeeper.saas.report.service.impl.SeriousFragmentImpl;
import com.jinke.housekeeper.saas.report.service.listener.SeriousFragmentListener;
import com.jinke.housekeeper.saas.report.view.SeriousFragmentView;
import com.lidroid.xutils.http.RequestParams;

/**
 * Created by Administrator on 2017/9/20.
 */

public class SeriousFragmentPresenter extends BasePresenter<SeriousFragmentView> implements SeriousFragmentListener {
    private Context mContext;
    private SeriousFragmentBiz mBiz;

    public SeriousFragmentPresenter(Context mContext) {
        this.mContext = mContext;
        mBiz = new SeriousFragmentImpl(mContext);
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
    public void getappHandleDataFailure(String string) {
        mView.hideLoading();
        mView.getappHandleDataFailure(string);
    }
}

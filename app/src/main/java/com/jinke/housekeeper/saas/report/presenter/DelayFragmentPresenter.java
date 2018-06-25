package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.service.DelayFragmentBiz;
import com.jinke.housekeeper.saas.report.service.impl.DelayFragmentImpl;
import com.jinke.housekeeper.saas.report.service.listener.DelayFragmentListener;
import com.jinke.housekeeper.saas.report.view.DelayFragmentView;
import com.lidroid.xutils.http.RequestParams;

/**
 * Created by Administrator on 2017/9/18.
 */

public class DelayFragmentPresenter extends BasePresenter<DelayFragmentView> implements DelayFragmentListener {
    private Context context;
    private DelayFragmentBiz mBiz;

    public DelayFragmentPresenter(Context context) {
        this.context = context;
        mBiz = new DelayFragmentImpl(context);
    }

    public void getappHandleData(RequestParams params) {
        mView.showLoading();
        mBiz.getappHandleData(params,this);
    }

    @Override
    public void showToast(String s) {
        mView.hideLoading();
        mView.getappHandleshowToast(s);
    }

    @Override
    public void getappHandleDataonSuccess() {
        mView.hideLoading();
        mView.getappHandleDataonSuccess();
    }
}

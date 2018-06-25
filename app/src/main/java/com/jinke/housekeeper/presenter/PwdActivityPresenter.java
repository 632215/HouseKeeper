package com.jinke.housekeeper.presenter;

import android.content.Context;
import android.util.Log;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.bean.AppHandleInfo;
import com.jinke.housekeeper.service.biz.PwdActivityBiz;
import com.jinke.housekeeper.service.impl.PwdActivityImpl;
import com.jinke.housekeeper.service.listener.PwdActivityListener;
import com.jinke.housekeeper.service.listener.UsersFragmentListener;
import com.jinke.housekeeper.view.PwdActivityView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/7.
 */

public class PwdActivityPresenter extends BasePresenter<PwdActivityView> implements PwdActivityListener, UsersFragmentListener {
    private Context context;
    private PwdActivityBiz biz;

    public PwdActivityPresenter(Context context) {
        this.context = context;
        biz=new PwdActivityImpl(context);
    }

    public void getUpdatePwd(SortedMap<String, String> map) {
        mView.showLoading();
        biz.getUpdatePwd(map,this);
    }

    public void getLoinOuton(SortedMap<String, String> map) {
        mView.showLoading();
        biz.getLoginOut(map,this);
    }

    @Override
    public void getUpdatePwdError(String code, String msg) {
        mView.hideLoading();
        mView.getUpdatePwdError(code,  msg);
        Log.i("32s","PwdActivityPresenteronError");
    }

    @Override
    public void getUpdatePwdNext() {
        Log.i("32s","PwdActivityPresenteronNext");
        mView.hideLoading();
        mView.getUpdatePwdNext();
    }

    @Override
    public void getLoinOutonError(String code, String msg) {
        mView.hideLoading();
        mView.getLoinOutonError(code,  msg);
        Log.i("32s","PwdActivityPresenteronError");
    }

    @Override
    public void getLoinOutonNext(AppHandleInfo info) {
        Log.i("32s","PwdActivityPresenteronNext");
        mView.hideLoading();
        mView.getLoinOutonNext(info);
    }
}

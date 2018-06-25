package com.jinke.housekeeper.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.bean.TestInfo;
import com.jinke.housekeeper.service.biz.RegisterActivityBiz;
import com.jinke.housekeeper.service.impl.RegisterActivityImpl;
import com.jinke.housekeeper.service.listener.RegisterActivityListener;
import com.jinke.housekeeper.view.RegisterActivityView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/15.
 */

public class RegisterActivityPresenter extends BasePresenter<RegisterActivityView> implements RegisterActivityListener {
    private Context context;
    private RegisterActivityBiz biz;

    public RegisterActivityPresenter(Context context) {
        this.context = context;
        biz= new RegisterActivityImpl(context);
    }

    public void getRegisterData(SortedMap<String, String> map) {
        mView.showLoading();
        biz.getRegisterData(map,this);
    }

    @Override
    public void getRegisterDataonNext(TestInfo info) {
        mView.hideLoading();
        mView.getRegisterDataonNext(info);
    }

    @Override
    public void getRegisterDataonError(String code, String msg) {
        mView.hideLoading();
        mView.getRegisterDataonError(code,  msg);
    }
}

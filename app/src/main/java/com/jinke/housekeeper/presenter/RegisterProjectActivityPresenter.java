package com.jinke.housekeeper.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.bean.RegisterProjectBean;
import com.jinke.housekeeper.service.biz.RegisterProjectActivityBiz;
import com.jinke.housekeeper.service.impl.RegisterProjectActivityImpl;
import com.jinke.housekeeper.service.listener.RegisterProjectActivityListener;
import com.jinke.housekeeper.view.RegisterProjectActivityView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/15.
 */

public class RegisterProjectActivityPresenter extends BasePresenter<RegisterProjectActivityView> implements RegisterProjectActivityListener {
    private Context context;
    private RegisterProjectActivityBiz biz;

    public RegisterProjectActivityPresenter(Context context) {
        this.context = context;
        biz =new RegisterProjectActivityImpl(context);
    }

    public void getXMList(SortedMap<String, String> map) {
        mView.showLoading();
        biz.getXMList(map,this);
    }

    @Override
    public void getXMListonNext(RegisterProjectBean info) {
        mView.hideLoading();
        mView.getXMListonNext(info);
    }

    @Override
    public void getXMListonError(String code, String msg) {
        mView.hideLoading();
        mView.getXMListonError(code,  msg);
    }
}

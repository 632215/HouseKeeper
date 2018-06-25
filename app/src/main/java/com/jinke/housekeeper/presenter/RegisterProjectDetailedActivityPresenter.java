package com.jinke.housekeeper.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.bean.RegisterProjectBean;
import com.jinke.housekeeper.service.biz.RegisterProjectDetailedActivityBiz;
import com.jinke.housekeeper.service.impl.RegisterProjectDetailedActivityImpl;
import com.jinke.housekeeper.service.listener.RegisterProjectDetailedActivityListener;
import com.jinke.housekeeper.view.RegisterProjectDetailedActivityView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/15.
 */

public class RegisterProjectDetailedActivityPresenter extends BasePresenter<RegisterProjectDetailedActivityView> implements RegisterProjectDetailedActivityListener {
    private Context context;
    private RegisterProjectDetailedActivityBiz biz;

    public RegisterProjectDetailedActivityPresenter(Context context) {
        this.context = context;
        biz=new RegisterProjectDetailedActivityImpl(context);
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

package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.bean.RegisterProjectBean;
import com.jinke.housekeeper.service.biz.RegisterFirmActivityBiz;
import com.jinke.housekeeper.service.impl.RegisterFirmActivityImpl;
import com.jinke.housekeeper.service.listener.RegisterFirmActivityListener;
import com.jinke.housekeeper.view.RegisterFirmActivityView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/15.
 */

public class RegisterFirmActivityPresenter extends BasePresenter<RegisterFirmActivityView> implements RegisterFirmActivityListener {
    private Context context;
    private RegisterFirmActivityBiz biz;

    public RegisterFirmActivityPresenter(Context context) {
        this.context = context;
        biz = new RegisterFirmActivityImpl(context);
    }

    public void getXMList(SortedMap<String, String> map) {
        biz.getXMList(map, this);
    }

    @Override
    public void getXMListonError(String code, String msg) {
        if (mView != null)
            mView.getXMListonError(code, msg);
    }

    @Override
    public void getXMListonNext(RegisterProjectBean info) {
        if (mView != null)
            mView.getXMListonNext(info);
    }
}

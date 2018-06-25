package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.bean.ProcessDetailBean;
import com.jinke.housekeeper.saas.report.service.impl.CheckDetailsActivityImpl;
import com.jinke.housekeeper.saas.report.service.listener.CheckDetailsActivityListener;
import com.jinke.housekeeper.saas.report.view.CheckDetailsActivityView;
import com.jinke.housekeeper.saas.report.service.CheckDetailsActivityBiz;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class CheckDetailsActivityPresenter extends BasePresenter<CheckDetailsActivityView> implements CheckDetailsActivityListener {
    private Context context;
    CheckDetailsActivityBiz biz;

    public CheckDetailsActivityPresenter(Context context) {
        this.context = context;
        biz = new CheckDetailsActivityImpl(context);
    }

    public void getProcessDetail(SortedMap<String, String> map) {
        biz.getProcessDetail(map, this);
    }

    @Override
    public void getProcessDetailonNext(ProcessDetailBean info) {
        if (mView != null)
            mView.getProcessDetailOnNext(info);
    }

    @Override
    public void getProcessDetailonError(String code, String msg) {
        if (mView != null)
            mView.getProcessDetailonError(code, msg);
    }
}

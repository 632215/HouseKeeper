package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.bean.NodeDelayBean;
import com.jinke.housekeeper.saas.report.service.DelayProgressActivityBiz;
import com.jinke.housekeeper.saas.report.service.impl.DelayProgressActivityViewImpl;
import com.jinke.housekeeper.saas.report.service.listener.DelayProgressActivityViewListener;
import com.jinke.housekeeper.saas.report.view.DelayProgressActivityView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class DelayProgressActivityPresenter extends BasePresenter<DelayProgressActivityView> implements DelayProgressActivityViewListener {
    private Context context;
    private DelayProgressActivityBiz biz;

    public DelayProgressActivityPresenter(Context context) {
        this.context = context;
        biz = new DelayProgressActivityViewImpl(context);
    }

    public void getProcessDetail(SortedMap<String, String> map) {
        biz.getProcessDetail(map, this);
    }

    @Override
    public void getProcessDetailonNext(NodeDelayBean info) {
        if (mView != null)
            mView.getProcessDetailOnNext(info);
    }

    @Override
    public void getProcessDetailonError(String code, String msg) {
        if (mView != null)
            mView.getProcessDetailOnError(code, msg);
    }
}

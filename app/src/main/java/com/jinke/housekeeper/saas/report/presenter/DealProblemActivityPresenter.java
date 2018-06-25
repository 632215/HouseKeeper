package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.bean.NodeDetailBean;
import com.jinke.housekeeper.saas.report.service.DealProblemActivityBiz;
import com.jinke.housekeeper.saas.report.service.impl.DealProblemActivityImpl;
import com.jinke.housekeeper.saas.report.service.listener.DealProblemActivityListener;
import com.jinke.housekeeper.saas.report.view.DealProblemActivityView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/7.
 */

public class DealProblemActivityPresenter extends BasePresenter<DealProblemActivityView> implements DealProblemActivityListener {
    private Context context;
    private DealProblemActivityBiz biz;

    public DealProblemActivityPresenter(Context context) {
        this.context = context;
        biz = new DealProblemActivityImpl(context);
    }

    public void getProcessDetail(SortedMap<String, String> map) {
        biz.getProcessDetail(map, this);
    }

    @Override
    public void getProcessDetailonError(String code, String msg) {
        if (mView != null)
            mView.getProcessDetailonError(code, msg);
    }

    @Override
    public void getProcessDetailonNext(NodeDetailBean info) {
        if (mView != null)
            mView.getProcessDetailonNext(info);
    }
}

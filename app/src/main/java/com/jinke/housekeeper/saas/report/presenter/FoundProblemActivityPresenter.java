package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.bean.NodeDetailBean;
import com.jinke.housekeeper.saas.report.service.FoundProblemActivityBiz;
import com.jinke.housekeeper.saas.report.service.impl.FoundProblemActivityImpl;
import com.jinke.housekeeper.saas.report.service.listener.FoundProblemActivityListener;
import com.jinke.housekeeper.saas.report.view.FoundProblemActivityView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class FoundProblemActivityPresenter extends BasePresenter<FoundProblemActivityView> implements FoundProblemActivityListener {
    private Context context;
    private FoundProblemActivityBiz biz;

    public FoundProblemActivityPresenter(Context context) {
        this.context = context;
        biz = new FoundProblemActivityImpl(context);
    }

    public void getProcessDetail(SortedMap<String, String> map) {
        biz.getProcessDetail(map, this);
    }

    @Override
    public void getProcessDetailonNext(NodeDetailBean info) {
        if (mView != null)
            mView.getProcessDetailonNext(info);
    }

    @Override
    public void getProcessDetailonError(String code, String msg) {
        if (mView != null)
            mView.getProcessDetailonError(code, msg);
    }


    public void grabSingle(SortedMap<String, String> map) {
        biz.grabSingle(map, this);
    }

    @Override
    public void grabSingleonNext() {
        if (mView != null)
            mView.grabSingleonNext();
    }

    @Override
    public void grabSingleonError(String code, String msg) {
        if (mView != null)
            mView.grabSingleonError(code, msg);
    }
}

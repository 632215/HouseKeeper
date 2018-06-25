package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.bean.ProcessNodeBean;
import com.jinke.housekeeper.saas.report.service.RectProcessActivityBiz;
import com.jinke.housekeeper.saas.report.service.impl.RectProcessActivityImpl;
import com.jinke.housekeeper.saas.report.service.listener.RectProcessActivityListener;
import com.jinke.housekeeper.saas.report.view.RectProcessActivityView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class RectProcessActivityPresenter extends BasePresenter<RectProcessActivityView> implements RectProcessActivityListener {
    private Context context;
    RectProcessActivityBiz biz;

    public RectProcessActivityPresenter(Context context) {
        this.context = context;
        biz = new RectProcessActivityImpl(context);
    }

    public void getDetailsData(SortedMap<String, String> map) {
        biz.getDetailsData(map, this);
    }

    @Override
    public void getDetailsDataonNext(ProcessNodeBean processNodeBean) {
        if (mView != null)
            mView.getDetailsDataonNext(processNodeBean);
    }

    @Override
    public void getDetailsDataonError(String code, String msg) {
        if (mView != null)
            mView.getDetailsDataonError(code, msg);

    }
}

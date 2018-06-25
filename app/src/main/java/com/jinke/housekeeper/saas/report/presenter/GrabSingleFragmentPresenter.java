package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.bean.WorkOrderBean;
import com.jinke.housekeeper.saas.report.service.GrabSingleFragmentBiz;
import com.jinke.housekeeper.saas.report.service.impl.GrabSingleFragmentImpl;
import com.jinke.housekeeper.saas.report.service.listener.GrabSingleFragmentListener;
import com.jinke.housekeeper.saas.report.view.GrabSingleFragmentView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class GrabSingleFragmentPresenter extends BasePresenter<GrabSingleFragmentView> implements GrabSingleFragmentListener {
    private Context context;
    private GrabSingleFragmentBiz biz;

    public GrabSingleFragmentPresenter(Context context) {
        this.context = context;
        biz = new GrabSingleFragmentImpl(context);
    }

    public void initDate(SortedMap<String, String> map) {
        biz.initDate(map, this);
    }

    @Override
    public void initDateOnNext(WorkOrderBean info) {
        if (mView != null) {
            mView.initDateOnNext(info);
        }

    }

    @Override
    public void initDateOnError(String code, String msg) {
        if (mView != null) {
            mView.initDateOnError(code, msg);
        }
    }
}

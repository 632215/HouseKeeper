package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.bean.NodeElseBean;
import com.jinke.housekeeper.saas.report.bean.WorkBean;
import com.jinke.housekeeper.saas.report.service.ProcessDetailActivityBiz;
import com.jinke.housekeeper.saas.report.service.impl.ProcessDetailActivityImpl;
import com.jinke.housekeeper.saas.report.service.listener.ProcessDetailActivityListener;
import com.jinke.housekeeper.saas.report.view.ProcessDetailActivityView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class ProcessDetailActivityPresenter extends BasePresenter<ProcessDetailActivityView> implements ProcessDetailActivityListener {
    private Context context;
    private ProcessDetailActivityBiz biz;

    public ProcessDetailActivityPresenter(Context context) {
        this.context = context;
        biz = new ProcessDetailActivityImpl(context);
    }

    public void getWorkDetail(SortedMap<String, String> map) {
        mView.showLoading();
        biz.getWorkDetail(map, this);
    }

    @Override
    public void getWorkDetailonError(String code, String msg) {
        mView.hideLoading();
        mView.getWorkDetailonError(code, msg);
    }

    @Override
    public void getWorkDetailonNext(WorkBean.ObjBean workBean) {
        mView.hideLoading();
        mView.getWorkDetailonNext(workBean);
    }


    public void getProcessDetail(SortedMap<String, String> map) {
        mView.showLoading();
        biz.getProcessDetail(map, this);
    }

    @Override
    public void getProcessDetailonNext(NodeElseBean info) {
        mView.hideLoading();
        mView.getProcessDetailonNext(info);
    }

    @Override
    public void getProcessDetailonError(String code, String msg) {
        mView.hideLoading();
        mView.getProcessDetailonError(code,  msg);
    }
}

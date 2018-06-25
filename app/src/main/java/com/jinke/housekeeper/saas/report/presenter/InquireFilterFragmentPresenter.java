package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.bean.AllReportBean;
import com.jinke.housekeeper.saas.report.service.InquireFilterFragmentBiz;
import com.jinke.housekeeper.saas.report.service.impl.InquireFilterFragmentImpl;
import com.jinke.housekeeper.saas.report.service.listener.InquireFilterFragmentListener;
import com.jinke.housekeeper.saas.report.view.InquireFilterFragmentView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class InquireFilterFragmentPresenter extends BasePresenter<InquireFilterFragmentView> implements InquireFilterFragmentListener {
    private Context context;
    private InquireFilterFragmentBiz biz;

    public InquireFilterFragmentPresenter(Context context) {
        this.context = context;
        biz = new InquireFilterFragmentImpl(context);
    }

    public void getAllReportList(SortedMap<String, String> map) {
        biz.getAllReportList(map, this);
    }

    @Override
    public void getAllReportListonError(String code, String msg) {
        if (mView != null)
            mView.getAllReportListonError(code, msg);
    }

    @Override
    public void getAllReportListonNext(AllReportBean bean) {
        if (mView != null)
            mView.getAllReportListonNext(bean);

    }
}

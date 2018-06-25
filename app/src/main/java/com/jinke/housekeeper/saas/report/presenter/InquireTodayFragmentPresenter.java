package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.bean.AllReportBean;
import com.jinke.housekeeper.saas.report.service.InquireTodayFragmentBiz;
import com.jinke.housekeeper.saas.report.service.impl.InquireTodayFragmentImpl;
import com.jinke.housekeeper.saas.report.service.listener.InquireTodayFragmentListener;
import com.jinke.housekeeper.saas.report.view.InquireTodayFragmentView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class InquireTodayFragmentPresenter extends BasePresenter<InquireTodayFragmentView> implements InquireTodayFragmentListener {
    private Context context;
    private InquireTodayFragmentBiz biz;

    public InquireTodayFragmentPresenter(Context context) {
        this.context = context;
        biz = new InquireTodayFragmentImpl(context);
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

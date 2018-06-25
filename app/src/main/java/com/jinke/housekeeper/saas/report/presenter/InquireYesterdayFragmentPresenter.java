package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.bean.AllReportBean;
import com.jinke.housekeeper.saas.report.service.InquireYesterdayFragmentBiz;
import com.jinke.housekeeper.saas.report.service.impl.InquireYesterdayFragmentImpl;
import com.jinke.housekeeper.saas.report.service.listener.InquireYesterdayFragmentListener;
import com.jinke.housekeeper.saas.report.view.InquireYesterdayFragmentView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class InquireYesterdayFragmentPresenter extends BasePresenter<InquireYesterdayFragmentView> implements InquireYesterdayFragmentListener {
    private Context context;
    private InquireYesterdayFragmentBiz biz;

    public InquireYesterdayFragmentPresenter(Context context) {
        this.context = context;
        biz = new InquireYesterdayFragmentImpl(context);
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

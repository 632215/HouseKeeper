package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.bean.AllReportBean;
import com.jinke.housekeeper.saas.report.service.InquireAllFragmentBiz;
import com.jinke.housekeeper.saas.report.service.impl.InquireAllFragmentImpl;
import com.jinke.housekeeper.saas.report.service.listener.InquireAllFragmentListener;
import com.jinke.housekeeper.saas.report.view.InquireAllFragmentView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class InquireAllFragmentPresenter extends BasePresenter<InquireAllFragmentView> implements InquireAllFragmentListener {
    private Context context;
    private InquireAllFragmentBiz biz;

    public InquireAllFragmentPresenter(Context context) {
        this.context = context;
        biz = new InquireAllFragmentImpl(context);
    }

    public void getAllReportList(SortedMap<String, String> map) {
        biz.getAllReportList(map, this);
    }

    @Override
    public void getAllReportListonNext(AllReportBean bean) {
        if (mView != null) {
            mView.getAllReportListonNext(bean);
        }
    }

    @Override
    public void getAllReportListonErrorbean(String code, String msg) {
        if (mView != null)
            mView.getAllReportListonErrorbean(code, msg);
    }


}

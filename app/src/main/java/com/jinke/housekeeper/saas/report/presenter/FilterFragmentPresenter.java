package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.bean.RectifiedBean;
import com.jinke.housekeeper.saas.report.service.impl.FilterFragmentImpl;
import com.jinke.housekeeper.saas.report.service.listener.FilterFragmentListener;
import com.jinke.housekeeper.saas.report.view.FilterFragmentView;
import com.jinke.housekeeper.saas.report.service.FilterFragmentBiz;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class FilterFragmentPresenter extends BasePresenter<FilterFragmentView> implements FilterFragmentListener {
    private Context context;
    private FilterFragmentBiz biz;

    public FilterFragmentPresenter(Context context) {
        this.context = context;
        biz = new FilterFragmentImpl(context);
    }

    public void getRectifiedList(SortedMap<String, String> map) {
        biz.getRectifiedList(map, this);
    }

    @Override
    public void getRectifiedListonNext(RectifiedBean bean) {
        if (mView != null)
            mView.getRectifiedListonNext(bean);
    }

    @Override
    public void getRectifiedListonError(String code, String msg) {
        if (mView != null)
            mView.getRectifiedListonError(code, msg);
    }
}

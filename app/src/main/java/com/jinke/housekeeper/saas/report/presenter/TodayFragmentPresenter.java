package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.bean.RectifiedBean;
import com.jinke.housekeeper.saas.report.service.TodayFragmentBiz;
import com.jinke.housekeeper.saas.report.service.impl.TodayFragmentImpl;
import com.jinke.housekeeper.saas.report.service.listener.TodayFragmentListener;
import com.jinke.housekeeper.saas.report.view.TodayFragmentView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class TodayFragmentPresenter extends BasePresenter<TodayFragmentView> implements TodayFragmentListener {
    private Context context;
    private TodayFragmentBiz biz;

    public TodayFragmentPresenter(Context context) {
        this.context = context;
        biz = new TodayFragmentImpl(context);
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

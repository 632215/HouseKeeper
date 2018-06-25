package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.bean.RectifiedBean;
import com.jinke.housekeeper.saas.report.service.YesterdayFragmentBiz;
import com.jinke.housekeeper.saas.report.service.impl.YesterdayFragmentImpl;
import com.jinke.housekeeper.saas.report.service.listener.YesterdayFragmentListener;
import com.jinke.housekeeper.saas.report.view.YesterdayFragmentView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class YesterdayFragmentPresenter extends BasePresenter<YesterdayFragmentView> implements YesterdayFragmentListener {
    private Context context;
    private YesterdayFragmentBiz biz;

    public YesterdayFragmentPresenter(Context context) {
        this.context = context;
        biz = new YesterdayFragmentImpl(context);
    }

    public void getRectifiedList(SortedMap<String, String> map) {
        biz.getRectifiedList(map, this);
    }

    @Override
    public void getRectifiedListonError(String code, String msg) {
        if (mView != null)
            mView.getRectifiedListonError(code, msg);
    }

    @Override
    public void getRectifiedListonNext(RectifiedBean bean) {
        if (mView != null)
            mView.getRectifiedListonNext(bean);
    }
}

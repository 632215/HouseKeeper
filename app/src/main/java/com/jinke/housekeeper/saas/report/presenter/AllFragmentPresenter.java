

package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.bean.RectifiedBean;
import com.jinke.housekeeper.saas.report.service.impl.AllFragmentImpl;
import com.jinke.housekeeper.saas.report.service.listener.AllFragmentListener;
import com.jinke.housekeeper.saas.report.view.AllFragmentView;
import com.jinke.housekeeper.saas.report.service.AllFragmentBiz;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */
public class AllFragmentPresenter extends BasePresenter<AllFragmentView> implements AllFragmentListener {
    private Context context;
    private AllFragmentBiz biz;

    public AllFragmentPresenter(Context context) {
        this.context = context;
        biz = new AllFragmentImpl(context);
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


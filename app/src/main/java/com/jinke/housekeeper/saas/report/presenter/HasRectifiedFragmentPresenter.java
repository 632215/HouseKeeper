package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.service.impl.HasRectifiedFragmentImpl;
import com.jinke.housekeeper.saas.report.service.listener.HasRectifiedFragmentListener;
import com.jinke.housekeeper.saas.report.view.HasRectifiedFragmentView;
import com.jinke.housekeeper.saas.report.service.HasRectifiedFragmentBiz;

/**
 * Created by Administrator on 2017/9/11.
 */

public class HasRectifiedFragmentPresenter extends BasePresenter<HasRectifiedFragmentView> implements HasRectifiedFragmentListener {
    private Context context;
    HasRectifiedFragmentBiz biz;

    public HasRectifiedFragmentPresenter(Context context) {
        this.context = context;
        biz = new HasRectifiedFragmentImpl(context);
    }

}

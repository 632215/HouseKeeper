package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.bean.SelfHistoryBean;
import com.jinke.housekeeper.saas.report.service.PersonFilterFragmentBiz;
import com.jinke.housekeeper.saas.report.service.impl.PersonFilterFragmentImpl;
import com.jinke.housekeeper.saas.report.service.listener.PersonFilterFragmentListener;
import com.jinke.housekeeper.saas.report.view.PersonFilterFragmentView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class PersonFilterFragmentPresenter extends BasePresenter<PersonFilterFragmentView> implements PersonFilterFragmentListener {
    private Context context;
    PersonFilterFragmentBiz biz;

    public PersonFilterFragmentPresenter(Context context) {
        this.context = context;
        biz = new PersonFilterFragmentImpl(context);
    }

    public void getSelfHistoryList(SortedMap<String, String> map) {
        biz.getSelfHistoryList(map, this);
    }

    @Override
    public void getSelfHistoryListonError(String code, String msg) {
        if (mView != null)
            mView.getSelfHistoryListonError(code, msg);
    }

    @Override
    public void getSelfHistoryListonNext(SelfHistoryBean bean) {
        if (mView != null)
            mView.getSelfHistoryListonNext(bean);
    }
}

package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.bean.SelfHistoryBean;
import com.jinke.housekeeper.saas.report.service.PersonTodayFragmentBiz;
import com.jinke.housekeeper.saas.report.service.impl.PersonTodayFragmentImpl;
import com.jinke.housekeeper.saas.report.service.listener.PersonTodayFragmentListener;
import com.jinke.housekeeper.saas.report.view.PersonTodayFragmentView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class PersonTodayFragmentPresenter extends BasePresenter<PersonTodayFragmentView> implements PersonTodayFragmentListener {
    private Context context;

    private PersonTodayFragmentBiz biz;

    public PersonTodayFragmentPresenter(Context context) {
        this.context = context;
        biz = new PersonTodayFragmentImpl(context);
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

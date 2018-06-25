package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.bean.SelfHistoryBean;
import com.jinke.housekeeper.saas.report.service.PersonAllFragmentBiz;
import com.jinke.housekeeper.saas.report.service.impl.PersonAllFragmentImpl;
import com.jinke.housekeeper.saas.report.service.listener.PersonAllFragmentListener;
import com.jinke.housekeeper.saas.report.view.PersonAllFragmentView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class PersonAllFragmentPresenter extends BasePresenter<PersonAllFragmentView> implements PersonAllFragmentListener {
    private Context context;
    PersonAllFragmentBiz biz;

    public PersonAllFragmentPresenter(Context context) {
        this.context = context;
        biz = new PersonAllFragmentImpl(context);
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

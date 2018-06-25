package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.bean.SelfHistoryBean;
import com.jinke.housekeeper.saas.report.service.PersonYesterdayFragmentBiz;
import com.jinke.housekeeper.saas.report.service.impl.PersonYesterdayFragmentImpl;
import com.jinke.housekeeper.saas.report.service.listener.PersonYesterdayFragmentListener;
import com.jinke.housekeeper.saas.report.view.PersonYesterdayFragmentView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class PersonYesterdayFragmentPresenter extends BasePresenter<PersonYesterdayFragmentView> implements PersonYesterdayFragmentListener {
    private Context context;
    PersonYesterdayFragmentBiz biz;

    public PersonYesterdayFragmentPresenter(Context context) {
        this.context = context;
        biz = new PersonYesterdayFragmentImpl(context);
    }

    public void getSelfHistoryList(SortedMap<String, String> map) {
        biz.getSelfHistoryList(map, this);
    }

    @Override
    public void getSelfHistoryListonNext(SelfHistoryBean bean) {
        if (mView != null)
            mView.getSelfHistoryListonNext(bean);
    }

    @Override
    public void getSelfHistoryListonError(String code, String msg) {
        if (mView != null)
            mView.getSelfHistoryListonError(code, msg);
    }
}

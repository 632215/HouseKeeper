package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.bean.RegisterDepartmentBean;
import com.jinke.housekeeper.saas.report.service.ContactsActivityBiz;
import com.jinke.housekeeper.saas.report.service.impl.ContactsActivityImpl;
import com.jinke.housekeeper.saas.report.service.listener.ContactsActivityListener;
import com.jinke.housekeeper.saas.report.view.ContactsActivityView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class ContactsActivityPresenter extends BasePresenter<ContactsActivityView> implements ContactsActivityListener {
    private Context context;
    private ContactsActivityBiz biz;

    public ContactsActivityPresenter(Context context) {
        this.context = context;
        biz = new ContactsActivityImpl(context);
    }

    public void getUserListData(SortedMap<String, String> map) {
        biz.getUserListData(map, this);
    }

    @Override
    public void getUserListDataonError(String code, String msg) {
        if (mView != null)
            mView.getUserListDataOnError(code, msg);
    }

    @Override
    public void getUserListDataonNext(RegisterDepartmentBean info) {
        if (mView != null)
            mView.getUserListDataOnNext(info);
    }
}

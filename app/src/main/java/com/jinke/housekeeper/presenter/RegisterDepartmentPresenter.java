package com.jinke.housekeeper.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.bean.RegisterDepartmentBean;
import com.jinke.housekeeper.service.biz.RegisterDepartmentActivityBiz;
import com.jinke.housekeeper.service.impl.RegisterDepartmentActivityImpl;
import com.jinke.housekeeper.service.listener.RegisterDepartmentActivityListener;
import com.jinke.housekeeper.view.RegisterDepartmentActivityView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/15.
 */

public class RegisterDepartmentPresenter extends BasePresenter<RegisterDepartmentActivityView> implements RegisterDepartmentActivityListener {
    private Context context;
    private RegisterDepartmentActivityBiz biz;

    public RegisterDepartmentPresenter(Context context) {
        this.context = context;
        biz = new RegisterDepartmentActivityImpl(context);
    }

    public void getCJContentData(SortedMap<String, String> map) {
        biz.getCJContentData(map, this);
    }

    @Override
    public void getCJContentDataOnError(String code, String msg) {
        if (mView != null)
            mView.getCJContentDataOnError(code, msg);
    }

    @Override
    public void getCJContentDataOnNext(RegisterDepartmentBean info) {
        if (mView != null)
            mView.getCJContentDataonNext(info);
    }
}

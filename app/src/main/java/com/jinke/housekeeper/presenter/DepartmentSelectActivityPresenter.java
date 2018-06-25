package com.jinke.housekeeper.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.bean.RegisterDepartmentBean;
import com.jinke.housekeeper.service.biz.DepartmentSelectActivityBiz;
import com.jinke.housekeeper.service.impl.DepartmentSelectActivityImpl;
import com.jinke.housekeeper.service.listener.DepartmentSelectActivityListener;
import com.jinke.housekeeper.view.DepartmentSelectActivityView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/7.
 */

public class DepartmentSelectActivityPresenter extends BasePresenter<DepartmentSelectActivityView> implements DepartmentSelectActivityListener {
    private Context context;
    private DepartmentSelectActivityBiz biz;

    public DepartmentSelectActivityPresenter(Context context) {
        this.context = context;
        biz=new DepartmentSelectActivityImpl(context);
    }

    public void getCJContentData(SortedMap<String, String> map) {
        mView.showLoading();
        biz.getCJContentData(map,this);}

    @Override
    public void getCJContentDataonNext(RegisterDepartmentBean info) {
        mView.hideLoading();
        mView.getCJContentDataonNext(info);
    }

    @Override
    public void getCJContentDataonError(String code, String msg) {
        mView.hideLoading();
        mView.getCJContentDataonError( code,  msg);
    }
}

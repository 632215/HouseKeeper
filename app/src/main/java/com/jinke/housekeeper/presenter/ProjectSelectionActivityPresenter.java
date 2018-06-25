package com.jinke.housekeeper.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.bean.CountXMListInfo;
import com.jinke.housekeeper.service.biz.ProjectSelectionActivityBiz;
import com.jinke.housekeeper.service.impl.ProjectSelectionActivityImpl;
import com.jinke.housekeeper.service.listener.ProjectSelectionActivityListener;
import com.jinke.housekeeper.view.ProjectSelectionActivityView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/7.
 */

public class ProjectSelectionActivityPresenter extends BasePresenter<ProjectSelectionActivityView> implements ProjectSelectionActivityListener {
    private Context context;
    private ProjectSelectionActivityBiz biz;

    public ProjectSelectionActivityPresenter(Context context) {
        this.context = context;
        biz =new ProjectSelectionActivityImpl(context);
    }

    public void getCountXMList(SortedMap<String, String> map) {
        mView.showLoading();
        biz.getCountXMList(map,this);
    }

    @Override
    public void getCountXMListonNext(CountXMListInfo info) {
        mView.hideLoading();
        mView.getCountXMListonNext(info);
    }

    @Override
    public void getCountXMListonErrort(String code, String msg) {
        mView.hideLoading();
        mView.getCountXMListonErrort(code,  msg);
    }
}

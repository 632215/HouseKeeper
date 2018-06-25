package com.jinke.housekeeper.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.bean.LibAllInfo;
import com.jinke.housekeeper.service.biz.LibAllActivityBiz;
import com.jinke.housekeeper.service.impl.LibAllActivityImpl;
import com.jinke.housekeeper.service.listener.LibAllActivityListener;
import com.jinke.housekeeper.view.LibAllActivityView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class LibAllActivityPresenter extends BasePresenter<LibAllActivityView> implements LibAllActivityListener {
    private Context context;
    private LibAllActivityBiz biz;

    public LibAllActivityPresenter(Context context) {
        this.context = context;
        biz = new LibAllActivityImpl(context);
    }

    public void getScenePage(SortedMap<String, String> map) {
        mView.showLoading();
        biz.getScenePage(map, this);
    }

    @Override
    public void getScenePageonNext(LibAllInfo info) {
        mView.hideLoading();
        mView.getScenePageonNext(info);
    }

    @Override
    public void getScenePageonError(String code, String msg) {
        mView.hideLoading();
        mView.getScenePageonError(code,  msg);
    }
}

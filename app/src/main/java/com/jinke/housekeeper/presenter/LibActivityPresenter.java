package com.jinke.housekeeper.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.bean.KnowledgeInfo;
import com.jinke.housekeeper.service.biz.LibActivityBiz;
import com.jinke.housekeeper.service.impl.LibActivityImpl;
import com.jinke.housekeeper.service.listener.LibActivityListener;
import com.jinke.housekeeper.view.LibActivityView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class LibActivityPresenter extends BasePresenter<LibActivityView> implements LibActivityListener {
    private Context context;
    private LibActivityBiz biz;

    public LibActivityPresenter(Context context) {
        this.context = context;
        biz =new LibActivityImpl(context);
    }

    public void getLore(SortedMap<String, String> map) {
        mView.showLoading();
        biz.getLore(map,this);
    }

    @Override
    public void getLoreonNext(KnowledgeInfo info) {
        mView.hideLoading();
        mView.getLoreonNext(info);
    }

    @Override
    public void getLoreonError(String code, String msg) {
        mView.hideLoading();
        mView.getLoreonError(code,  msg);
    }
}

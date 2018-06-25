package com.jinke.housekeeper.presenter;

import android.content.Context;

import com.jinke.housekeeper.bean.TestInfo;
import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.service.biz.MemberDetailActivityBiz;
import com.jinke.housekeeper.service.impl.MemberDetailActivityImpl;
import com.jinke.housekeeper.service.listener.MemberDetailActivityListener;
import com.jinke.housekeeper.view.MemberDetailActivityView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/7.
 */

public class MemberDetailActivityPresenter extends BasePresenter<MemberDetailActivityView> implements MemberDetailActivityListener {
    private Context context;
    private MemberDetailActivityBiz mBiz;

    public MemberDetailActivityPresenter(Context context) {
        this.context = context;
        mBiz = new MemberDetailActivityImpl(context);
    }

    public void getUserDelete(SortedMap<String, String> map) {
    mView.showLoading();
        mBiz.getUserDelete(map,this);
    }

    @Override
    public void getUserDeleteNext(TestInfo testInfo) {
        mView.hideLoading();
        mView.getUserDeleteNext(testInfo);
    }

    @Override
    public void getUserDeleteError(String code, String msg) {
        mView.hideLoading();
        mView.getUserDeleteError(code,  msg);
    }
}

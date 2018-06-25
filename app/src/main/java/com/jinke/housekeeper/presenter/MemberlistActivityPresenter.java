package com.jinke.housekeeper.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.bean.MemberListBean;
import com.jinke.housekeeper.service.biz.MemberlistActivityBiz;
import com.jinke.housekeeper.service.impl.MemberlistActivityImpl;
import com.jinke.housekeeper.service.listener.MemberlistActivityListener;
import com.jinke.housekeeper.view.MemberlistActivityView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/7.
 */

public class MemberlistActivityPresenter extends BasePresenter<MemberlistActivityView> implements MemberlistActivityListener {
    private Context context;
    private MemberlistActivityBiz mBiz;

    public MemberlistActivityPresenter(Context context) {
        this.context = context;
        mBiz = new MemberlistActivityImpl(context);
    }

    /**
     * 获取人员名单
     *
     * @param map
     * @param isShow
     */
    public void getMenberList(SortedMap<String, String> map, boolean isShow) {
        mBiz.getMenberList(map, this, isShow);
    }

    @Override
    public void getMenberListNext(MemberListBean dataBean) {
        if (mView != null)
            mView.getMenberListNext(dataBean);
    }

    @Override
    public void getMenberListError(String code, String msg) {
        if (mView != null)
            mView.getMenberListError(code, msg);
    }
}

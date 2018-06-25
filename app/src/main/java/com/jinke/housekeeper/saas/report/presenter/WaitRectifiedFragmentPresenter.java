package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.bean.WaitRectifiedBean;
import com.jinke.housekeeper.saas.report.service.impl.WaitRectifiedFragmentImpl;
import com.jinke.housekeeper.saas.report.service.listener.WaitRectifiedFragmentListener;
import com.jinke.housekeeper.saas.report.view.WaitRectifiedFragmentView;
import com.jinke.housekeeper.service.biz.WaitRectifiedFragmentBiz;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class WaitRectifiedFragmentPresenter extends BasePresenter<WaitRectifiedFragmentView> implements WaitRectifiedFragmentListener {
    private Context context;
    private WaitRectifiedFragmentBiz biz;

    public WaitRectifiedFragmentPresenter(Context context) {
        this.context = context;
        biz = new WaitRectifiedFragmentImpl(context);
    }

    /**
     * 获取待整改列表
     *
     * @param map
     */
    public void getWaitList(SortedMap<String, String> map) {
        if (mView != null)
            biz.getWaitList(map, this);
    }

    @Override
    public void getWaitListonNext(WaitRectifiedBean waitRectifiedBean) {
        if (mView != null)
            mView.getWaitListonNext(waitRectifiedBean);
    }

    @Override
    public void getWaitListonError(String code, String msg) {
        if (mView != null)
            mView.getWaitListonError(code, msg);
    }


    /**
     * 任務签收
     *
     * @param map
     */
    public void getAppTaskSignData(SortedMap<String, String> map) {
        if (mView != null)
            biz.getAppTaskSignData(map, this);
    }

    @Override
    public void getAppTaskSignDataonError(String code, String msg) {
        if (mView != null)
            mView.getAppTaskSignDataonError(code, msg);
    }
}

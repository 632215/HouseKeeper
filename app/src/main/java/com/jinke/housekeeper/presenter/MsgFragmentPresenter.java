package com.jinke.housekeeper.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.bean.MsgBean;
import com.jinke.housekeeper.bean.PersonalTaskBean;
import com.jinke.housekeeper.service.listener.MsgFragmentListener;
import com.jinke.housekeeper.service.biz.MsgFragmentBiz;
import com.jinke.housekeeper.service.impl.MsgFragmentImpl;
import com.jinke.housekeeper.view.MsgFragmentView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/6.
 */

public class MsgFragmentPresenter extends BasePresenter<MsgFragmentView> implements MsgFragmentListener {
    private Context mContext;
    private MsgFragmentBiz mBiz;

    public MsgFragmentPresenter(Context mContext) {
        this.mContext = mContext;
        mBiz = new MsgFragmentImpl(mContext);
    }

    /**
     * 请求消息列表数据
     */
    public void requestMsgList(SortedMap<String, String> map) {
        mView.showLoading();
        mBiz.requestMsgList(map, this);
    }

    @Override
    public void requestMsgListNext(MsgBean info) {
        mView.hideLoading();
        mView.requestMsgListNext(info);
    }

    @Override
    public void requestMsgListError(String code, String msg) {
        mView.hideLoading();
        mView.requestMsgListError(code, msg);
    }

    /**
     * 请求我的问题数值数据
     */
    public void requestMsgData(SortedMap<String, String> map) {
        mView.showLoading();
        mBiz.requestMsgData(map, this);
    }

    @Override
    public void requestMsgDataNext(PersonalTaskBean info) {
        mView.hideLoading();
        mView.requestMsgDataNext(info);
    }

    @Override
    public void requestMsgDataError(String code, String msg) {
        mView.hideLoading();
        mView.requestMsgDataError(code, msg);
    }

    public void updateReadStatus(SortedMap<String, String> map, int p) {
        mView.showLoading();
        mBiz.updateReadStatus(map, this, p);
    }

    /**
     * 更新消息状态
     *
     * @param p
     */
    @Override
    public void updateReadStatusNext(int p) {
        mView.hideLoading();
        mView.updateReadStatusNext(p);
    }
}

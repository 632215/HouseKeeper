package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.bean.EmptyBean;
import com.jinke.housekeeper.bean.MsgBean;
import com.jinke.housekeeper.saas.report.service.NewsFragmentBiz;
import com.jinke.housekeeper.saas.report.service.impl.NewsFragmentImpl;
import com.jinke.housekeeper.saas.report.service.listener.NewsFragmentListener;
import com.jinke.housekeeper.saas.report.view.NewsFragmentView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/7.
 */

public class NewsFragmentPresenter extends BasePresenter<NewsFragmentView> implements NewsFragmentListener {
    private Context context;
    private NewsFragmentBiz mBiz;

    public NewsFragmentPresenter(Context context) {
        this.context = context;
        mBiz=new NewsFragmentImpl(context);   }

    /**
     * 获取消息列表
     */
    public void initMagData(SortedMap<String, String> map) {
        mBiz.initMagData(map,this);
    }

    @Override
    public void initMagDataNext(MsgBean info) {
        mView.hideLoading();
        mView.initMagDataNext(info);
    }

    @Override
    public void initMagDataError(String code, String msg) {
        mView.hideLoading();
        mView.initMagDataError(code,  msg);
    }

    /**
     * 更新消息状态
     */
    public void updateReadStatus(SortedMap<String, String> map, int p) {
        mBiz.updateReadStatus(map,this, p);
    }

    @Override
    public void updateReadStatusNext(EmptyBean info, int p) {
        mView.hideLoading();
        mView.updateReadStatusNext(info,p);
    }

    @Override
    public void updateReadStatusError(String code, String msg) {
        mView.hideLoading();
        mView.updateReadStatusError(code,  msg);
    }
}

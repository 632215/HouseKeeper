package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.bean.NoReadBean;
import com.jinke.housekeeper.saas.report.service.NewsActivityBiz;
import com.jinke.housekeeper.saas.report.service.impl.NewsActivityImpl;
import com.jinke.housekeeper.saas.report.service.listener.NewsActivityListener;
import com.jinke.housekeeper.saas.report.view.NewsActivityView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/7.
 */

public class NewsActivityPresenter extends BasePresenter<NewsActivityView> implements NewsActivityListener {
    private Context context;
    private NewsActivityBiz mBiz;

    public NewsActivityPresenter(Context context) {
        this.context = context;
        mBiz = new NewsActivityImpl(context);
    }

    /**
     * 消息条数
     */
    public void updateReadStatus(SortedMap<String, String> map) {
        mBiz.updateReadStatus(map, this);
    }

    @Override
    public void updateReadStatusNext(NoReadBean info) {
        if (mView != null)
            mView.updateReadStatusNext(info);
    }

    @Override
    public void updateReadStatusError(String code, String msg) {
        if (mView != null)
            mView.updateReadStatusError(code, msg);
    }
}

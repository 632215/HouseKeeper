package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.bean.KeyPointBean;
import com.jinke.housekeeper.saas.report.service.KeyPointsActivityBiz;
import com.jinke.housekeeper.saas.report.service.impl.KeyPointsActivityImpl;
import com.jinke.housekeeper.saas.report.service.listener.KeyPointsActivityListener;
import com.jinke.housekeeper.saas.report.view.KeyPointsActivityView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class KeyPointsActivityPresenter extends BasePresenter<KeyPointsActivityView> implements KeyPointsActivityListener {
    private Context context;
    private KeyPointsActivityBiz biz;

    public KeyPointsActivityPresenter(Context context) {
        this.context = context;
        biz = new KeyPointsActivityImpl(context);
    }

    public void getGJContentData(SortedMap<String, String> map) {
        biz.getGJContentData(map, this);
    }

    @Override
    public void getGJContentDataOnNext(KeyPointBean info) {
        if (mView != null)
            mView.getGJContentDataOnNext(info);
    }

    @Override
    public void getGJContentDataOnError(String code, String msg) {
        if (mView != null)
            mView.getGJContentDataOnError(code, msg);
    }
}

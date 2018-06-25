package com.jinke.housekeeper.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.bean.ScenePageInfo;
import com.jinke.housekeeper.service.biz.MainsActivityBiz;
import com.jinke.housekeeper.service.impl.MainsActivityImpl;
import com.jinke.housekeeper.service.listener.MainsActivityListener;
import com.jinke.housekeeper.view.MainsActivityView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/15.
 */

public class MainsActivityPresenter extends BasePresenter<MainsActivityView> implements MainsActivityListener {
    private Context context;
    private MainsActivityBiz biz;

    public MainsActivityPresenter(Context context) {
        this.context = context;
        biz = new MainsActivityImpl(context);
    }

    public void getAllScenePage(SortedMap<String, String> map) {
        biz.getAllScenePage(map, this);
    }

    @Override
    public void getAllScenePageonError(String code, String msg) {
        if (mView != null)
            mView.getAllScenePageOnError(code, msg);
    }

    @Override
    public void getAllScenePageonNext(ScenePageInfo info) {
        if (mView != null)
            mView.getAllScenePageOnNext(info);
    }
}

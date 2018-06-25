package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.service.GrabSingleActivityBiz;
import com.jinke.housekeeper.saas.report.service.impl.GrabSingleActivityImpl;
import com.jinke.housekeeper.saas.report.service.listener.GrabSingleActivityListener;
import com.jinke.housekeeper.saas.report.view.GrabSingleActivityView;

/**
 * Created by Administrator on 2017/9/11.
 */

public class GrabSingleActivityPresenter extends BasePresenter<GrabSingleActivityView> implements GrabSingleActivityListener {
    private GrabSingleActivityBiz biz;
    private Context context;

    public GrabSingleActivityPresenter(Context context) {
        this.context = context;
        biz =new GrabSingleActivityImpl(context);
    }
}

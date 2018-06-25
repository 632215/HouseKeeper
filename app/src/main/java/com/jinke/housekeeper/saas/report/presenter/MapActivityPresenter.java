package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.bean.MapPointBean;
import com.jinke.housekeeper.saas.report.service.MapActivityBiz;
import com.jinke.housekeeper.saas.report.service.impl.MapActivityImpl;
import com.jinke.housekeeper.saas.report.service.listener.MapActivityListener;
import com.jinke.housekeeper.saas.report.view.MapActivityView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class MapActivityPresenter extends BasePresenter<MapActivityView> implements MapActivityListener {
    private Context context;
    private MapActivityBiz biz;

    public MapActivityPresenter(Context context) {
        this.context = context;
        biz = new MapActivityImpl(context);
    }

    public void getMapPoint(SortedMap<String, String> map) {
        biz.getMapPoint(map, this);
    }

    @Override
    public void getMapPointOnNext(MapPointBean info) {
        if (mView != null)
            mView.getMapPointOnNext(info);
    }

    @Override
    public void getMapPointOnError(String code, String msg) {
        if (mView != null)
            mView.getMapPointOnError(code, msg);
    }
}

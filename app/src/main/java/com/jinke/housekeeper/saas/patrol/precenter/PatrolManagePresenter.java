package com.jinke.housekeeper.saas.patrol.precenter;

import android.content.Context;

import com.jinke.housekeeper.saas.patrol.base.BasePresenter;
import com.jinke.housekeeper.saas.patrol.bean.EmptyBean;
import com.jinke.housekeeper.saas.patrol.bean.PointBean;
import com.jinke.housekeeper.saas.patrol.bean.TimeOutTaskListBean;
import com.jinke.housekeeper.saas.patrol.config.PatrolConfig;
import com.jinke.housekeeper.saas.patrol.listener.OnDeletePointListener;
import com.jinke.housekeeper.saas.patrol.listener.OnRequestListener;
import com.jinke.housekeeper.saas.patrol.listener.OnTimeOutTaskListener;
import com.jinke.housekeeper.saas.patrol.service.PatrolManageBiz;
import com.jinke.housekeeper.saas.patrol.service.impl.PatrolManageBizImpl;
import com.jinke.housekeeper.saas.patrol.view.PatrolManageView;

import java.util.List;
import java.util.Map;

/**
 * Created by root on 17-7-24.
 */

public class PatrolManagePresenter extends BasePresenter<PatrolManageView> implements OnRequestListener<PointBean>
        , OnDeletePointListener<EmptyBean> ,OnTimeOutTaskListener<List<TimeOutTaskListBean>> {
    private Context mContext;
    private PatrolManageBiz patrolManageBiz;

    public PatrolManagePresenter(Context mContext) {
        this.mContext = mContext;
        patrolManageBiz = new PatrolManageBizImpl(mContext);
    }

    public void getPointList(Map<String, String> map) {
        if (null != mView) {
            if (null != PatrolConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("token", PatrolConfig.getTokenBean().getToken());
                patrolManageBiz.getPointList(map, this);
            } else {
                mView.showMessage();
            }
        }
    }

    public void getTimeOutTask(Map<String, String> map) {
        if (null != mView) {
            if (null != PatrolConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("token", PatrolConfig.getTokenBean().getToken());
                patrolManageBiz.getTimeOutTask(map, this);
            } else {
                mView.showMessage();
            }
        }
    }

    public void delPoint(Map<String, String> map) {
        if (null != mView) {
            if (null != PatrolConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("token", PatrolConfig.getTokenBean().getToken());
                patrolManageBiz.delPoint(map, this);
            } else {
                mView.showMessage();
            }
        }
    }

    @Override
    public void onSuccess(PointBean bean) {
        if (mView != null)
            mView.onRefreshData(bean.getListData());
    }

    @Override
    public void onDeleteSuccess(EmptyBean emptyBean) {
        if (mView != null)
            mView.onDeletePoint();
    }

    @Override
    public void onTimeOutTaskSuccess(List<TimeOutTaskListBean> timeOutTaskBean) {
        if (mView != null)
            mView.getTimeOutTask(timeOutTaskBean);
    }

    @Override
    public void onError(String Code, String Msg) {
        if (mView != null)
            mView.showMessage();
    }
}

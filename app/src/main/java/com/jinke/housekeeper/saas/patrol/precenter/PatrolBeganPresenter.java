package com.jinke.housekeeper.saas.patrol.precenter;

import android.content.Context;

import com.jinke.housekeeper.saas.patrol.base.BasePresenter;
import com.jinke.housekeeper.saas.patrol.bean.SignPatrolListBean;
import com.jinke.housekeeper.saas.patrol.config.PatrolConfig;
import com.jinke.housekeeper.saas.patrol.listener.OnRequestListener;
import com.jinke.housekeeper.saas.patrol.service.PatrolBeganBiz;
import com.jinke.housekeeper.saas.patrol.service.impl.PatrolBeganBizImpl;
import com.jinke.housekeeper.saas.patrol.view.PatrolBeganView;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/7/28
 */

public class PatrolBeganPresenter extends BasePresenter<PatrolBeganView> implements OnRequestListener<SignPatrolListBean> {
    private Context mContext;
    private PatrolBeganBiz patrolBeganBiz;

    public PatrolBeganPresenter(Context mContext) {
        this.mContext = mContext;
        patrolBeganBiz = new PatrolBeganBizImpl(mContext);
    }

    public void getIfSignOut(Map<String, String> map) {
        if (null != mView) {
            if (null != PatrolConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("token", PatrolConfig.getTokenBean().getToken());
                patrolBeganBiz.getIfSignOut(map, this);
            } else {
                mView.showMessage();
            }
        }
    }

    @Override
    public void onSuccess(SignPatrolListBean bean) {
        if (mView != null)
            mView.onSuccess(bean);
    }

    @Override
    public void onError(String Code, String Msg) {
        if (mView != null)
            mView.showMessage();
    }
}

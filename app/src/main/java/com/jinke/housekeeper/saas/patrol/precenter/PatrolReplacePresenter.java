package com.jinke.housekeeper.saas.patrol.precenter;

import android.content.Context;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.patrol.base.BasePresenter;
import com.jinke.housekeeper.saas.patrol.bean.EmptyBean;
import com.jinke.housekeeper.saas.patrol.config.PatrolConfig;
import com.jinke.housekeeper.saas.patrol.listener.PatrolReplaceListener;
import com.jinke.housekeeper.saas.patrol.service.AddPatrolBiz;
import com.jinke.housekeeper.saas.patrol.service.PatrolReplaceBiz;
import com.jinke.housekeeper.saas.patrol.service.impl.AddPatrolBizImpl;
import com.jinke.housekeeper.saas.patrol.service.impl.PatrolReplaceBizImpl;
import com.jinke.housekeeper.saas.patrol.view.PatrolReplaceView;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/11/13
 */

public class PatrolReplacePresenter extends BasePresenter<PatrolReplaceView> implements PatrolReplaceListener<EmptyBean> {

    private Context mContext;
    private PatrolReplaceBiz patrolReplaceBiz;

    public PatrolReplacePresenter(Context mContext) {
        this.mContext = mContext;
        patrolReplaceBiz = new PatrolReplaceBizImpl(mContext);
    }

    public void patrolReplace(Map<String, String> map) {
        if (null != mView) {
            if (null != PatrolConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("token", PatrolConfig.getTokenBean().getToken());
                patrolReplaceBiz.patrolReplace(map, this);
            } else {
                mView.onError(mContext.getString(R.string.null_token));
            }
        }
    }

    @Override
    public void onBackPatrolReplace(EmptyBean emptyBean) {
        if (null != mView)
            mView.replacePointSuccess();
    }

    @Override
    public void onError(String Code, String Msg) {
        if (null != mView)
            mView.onError(Msg);
    }
}

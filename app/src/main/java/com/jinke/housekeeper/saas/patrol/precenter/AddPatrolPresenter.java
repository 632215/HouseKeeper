package com.jinke.housekeeper.saas.patrol.precenter;

import android.content.Context;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.patrol.base.BasePresenter;
import com.jinke.housekeeper.saas.patrol.bean.EmptyBean;
import com.jinke.housekeeper.saas.patrol.config.PatrolConfig;
import com.jinke.housekeeper.saas.patrol.listener.IAddPointListener;
import com.jinke.housekeeper.saas.patrol.service.AddPatrolBiz;
import com.jinke.housekeeper.saas.patrol.service.impl.AddPatrolBizImpl;
import com.jinke.housekeeper.saas.patrol.view.AddPatrolView;

import java.util.Map;

/**
 * Created by root on 17-7-24.
 */

public class AddPatrolPresenter extends BasePresenter<AddPatrolView> implements IAddPointListener<EmptyBean> {

    private Context mContext;
    private AddPatrolBiz addPatrolBiz;

    public AddPatrolPresenter(Context mContext) {
        this.mContext = mContext;
        addPatrolBiz = new AddPatrolBizImpl(mContext);
    }

    public void addPoint(Map<String, String> map) {
        if (null != mView) {
            if (null != PatrolConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("token", PatrolConfig.getTokenBean().getToken());
                addPatrolBiz.addPoint(map, this);
            } else {
                mView.onError(mContext.getString(R.string.null_token));
            }
        }
    }

    @Override
    public void onBackAddPoint(EmptyBean emptyBean) {
        if (null != mView)
            mView.addPatrolSuccess();
    }

    @Override
    public void onError(String Code, String Msg) {
        if (null != mView)
            mView.onError(Msg);
    }
}

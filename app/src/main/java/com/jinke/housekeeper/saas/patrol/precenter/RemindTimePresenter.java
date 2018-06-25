package com.jinke.housekeeper.saas.patrol.precenter;

import android.content.Context;

import com.jinke.housekeeper.saas.patrol.base.BasePresenter;
import com.jinke.housekeeper.saas.patrol.bean.RemindTimeBean;
import com.jinke.housekeeper.saas.patrol.config.PatrolConfig;
import com.jinke.housekeeper.saas.patrol.listener.OnRequestListener;
import com.jinke.housekeeper.saas.patrol.service.RemindTimeBiz;
import com.jinke.housekeeper.saas.patrol.service.impl.RemindTimeBizImpl;
import com.jinke.housekeeper.saas.patrol.utils.RemindTimeView;

import java.util.Map;

/**
 * Created by root on 17-7-24.
 */

public class RemindTimePresenter extends BasePresenter<RemindTimeView> implements OnRequestListener<RemindTimeBean>{
    private Context mContext;
    private RemindTimeBiz remindTimeBiz;

    public RemindTimePresenter(Context mContext) {
        this.mContext = mContext;
        remindTimeBiz = new RemindTimeBizImpl(mContext);
    }

    public void getRemindTime(Map<String, String> map) {
        if (null != mView) {
            if (null != PatrolConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("token", PatrolConfig.getTokenBean().getToken());
                remindTimeBiz.getRemindTime(map, this);
            } else {
                mView.showMessage();
            }
        }
    }

    @Override
    public void onSuccess(RemindTimeBean bean) {
        if (mView != null)
            mView.onRefreshData(bean);
    }


    @Override
    public void onError(String Code, String Msg) {
        if (mView != null)
            mView.showMessage();
    }
}

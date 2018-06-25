package com.jinke.housekeeper.saas.patrol.precenter;

import android.content.Context;

import com.jinke.housekeeper.saas.patrol.base.BasePresenter;
import com.jinke.housekeeper.saas.patrol.bean.TimeDataBean;
import com.jinke.housekeeper.saas.patrol.config.PatrolConfig;
import com.jinke.housekeeper.saas.patrol.listener.OnRequestListener;
import com.jinke.housekeeper.saas.patrol.service.PatrolStatCaleBiz;
import com.jinke.housekeeper.saas.patrol.service.impl.PatrolStatCaleBizIml;
import com.jinke.housekeeper.saas.patrol.view.PatrolStatCaleView;

import java.util.List;
import java.util.Map;

/**
 * author : huominghao
 * date : 2018/3/2 0002
 * function :
 */

public class PatrolStatCalePresenter extends BasePresenter<PatrolStatCaleView> implements OnRequestListener<List<TimeDataBean>> {

    private PatrolStatCaleBiz requestBiz;

    public PatrolStatCalePresenter(Context context) {
        requestBiz = new PatrolStatCaleBizIml(context);
    }

    public void timeData(Map<String, String> map) {
        if (null != mView) {
            if (null != PatrolConfig.getTokenBean().getToken()) {
                map.put("token", PatrolConfig.getTokenBean().getToken());
                requestBiz.timeData(map, this);
            } else {
                mView.onError("登录失效请重新登录！");
            }
        }
    }

    @Override
    public void onSuccess(List<TimeDataBean> timeDataBeans) {
        if (null != mView) {
            mView.timeData(timeDataBeans);
        }
    }

    @Override
    public void onError(String Code, String Msg) {
        if (null != mView) {
            mView.onError("登录失效请重新登录！");
        }
    }
}

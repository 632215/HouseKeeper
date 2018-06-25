package com.jinke.housekeeper.saas.patrol.precenter;

import android.content.Context;

import com.jinke.housekeeper.saas.patrol.base.BasePresenter;
import com.jinke.housekeeper.saas.patrol.bean.PointDataBean;
import com.jinke.housekeeper.saas.patrol.config.PatrolConfig;
import com.jinke.housekeeper.saas.patrol.listener.OnRequestListener;
import com.jinke.housekeeper.saas.patrol.service.StatementBiz;
import com.jinke.housekeeper.saas.patrol.service.impl.StatementBizIml;
import com.jinke.housekeeper.saas.patrol.view.StatementView;

import java.util.Map;

/**
 * author : huominghao
 * date : 2018/3/2 0002
 * function :
 */

public class StatementPresenter  extends BasePresenter<StatementView>
        implements OnRequestListener<PointDataBean>  {

    private StatementBiz requestBiz;

    public StatementPresenter(Context context) {
        requestBiz = new StatementBizIml(context);
    }

    public void pointData(Map<String, String> map) {
        if (null != PatrolConfig.getTokenBean().getToken()) {
            map.put("token", PatrolConfig.getTokenBean().getToken());
            requestBiz.pointData(map, this);
        } else {
            mView.onError("未获取Token");
        }
    }

    @Override
    public void onSuccess(PointDataBean dataBean) {
        if (null != mView) {
            mView.isStartBean(dataBean);
        }
    }



    @Override
    public void onError(String code, String msg) {
        if (null != mView){
            mView.onError(msg);
        }
    }
}

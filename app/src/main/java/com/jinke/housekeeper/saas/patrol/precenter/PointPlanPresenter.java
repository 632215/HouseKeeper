package com.jinke.housekeeper.saas.patrol.precenter;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.saas.patrol.base.BasePresenter;
import com.jinke.housekeeper.saas.patrol.bean.IsStartBean;
import com.jinke.housekeeper.saas.patrol.bean.PointPlanBean;
import com.jinke.housekeeper.saas.patrol.config.PatrolConfig;
import com.jinke.housekeeper.saas.patrol.listener.OnRequestListener;
import com.jinke.housekeeper.saas.patrol.listener.PointPlanListener;
import com.jinke.housekeeper.saas.patrol.service.PointPlanBiz;
import com.jinke.housekeeper.saas.patrol.service.impl.PointPlanBizIml;
import com.jinke.housekeeper.saas.patrol.view.PointPlanView;
import com.jinke.housekeeper.utils.CommonlyUtils;

import java.util.Map;

/**
 * author : huominghao
 * date : 2018/1/25 0025
 * function :
 */

public class PointPlanPresenter extends BasePresenter<PointPlanView> implements OnRequestListener<PointPlanBean>, PointPlanListener<IsStartBean> {
    private PointPlanBiz requestBiz;

    public PointPlanPresenter() {
        requestBiz = new PointPlanBizIml(MyApplicaption.getInstance());
    }

    public void isStart(Map<String, String> map) {
        if (null != PatrolConfig.getTokenBean().getToken()) {
            map.put("token", PatrolConfig.getTokenBean().getToken());
            requestBiz.isStart(map, this);
        } else {
            mView.onError("未获取Token");
        }

    }

    public void getTaskInfo(Map<String, String> map) {
        if (null != PatrolConfig.getTokenBean().getToken()) {
            map.put("token", PatrolConfig.getTokenBean().getToken());
            map.put("projectId", CommonlyUtils.getUserInfo(MyApplicaption.getInstance()).getLeftOrgId());
            requestBiz.getTaskInfo(map, this);
        } else {
            mView.onError("未获取Token");
        }

    }

    @Override
    public void onSuccess(PointPlanBean pointPlanBean) {
        if (null != mView) {
            mView.getPointListBean(pointPlanBean);
        }
    }

    @Override
    public void onPointPlanList(IsStartBean isStartBean) {
        if (null != mView) {
            mView.isStartBean(isStartBean);
        }
    }

    @Override
    public void onError(String code, String msg) {
        if (null != mView){
            mView.onError(msg);
        }
    }
}
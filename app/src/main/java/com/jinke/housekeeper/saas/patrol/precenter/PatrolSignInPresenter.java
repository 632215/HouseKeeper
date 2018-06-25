package com.jinke.housekeeper.saas.patrol.precenter;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.saas.patrol.base.BasePresenter;
import com.jinke.housekeeper.saas.patrol.bean.PatrolRecordBean;
import com.jinke.housekeeper.saas.patrol.bean.PointBean;
import com.jinke.housekeeper.saas.patrol.bean.SignPatrolBean;
import com.jinke.housekeeper.saas.patrol.bean.SystemTimeBean;
import com.jinke.housekeeper.saas.patrol.config.PatrolConfig;
import com.jinke.housekeeper.saas.patrol.listener.IPatrolRecordListener;
import com.jinke.housekeeper.saas.patrol.listener.IPunchCardListener;
import com.jinke.housekeeper.saas.patrol.listener.ISignInListener;
import com.jinke.housekeeper.saas.patrol.listener.ISignOutListener;
import com.jinke.housekeeper.saas.patrol.listener.ISystemTimeListener;
import com.jinke.housekeeper.saas.patrol.listener.PointListListener;
import com.jinke.housekeeper.saas.patrol.service.PatrolSignInBiz;
import com.jinke.housekeeper.saas.patrol.service.impl.PatrolSignInBizIml;
import com.jinke.housekeeper.saas.patrol.view.PatrolSignInView;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/7/25
 */

public class PatrolSignInPresenter extends BasePresenter<PatrolSignInView>
        implements ISystemTimeListener<SystemTimeBean>, ISignInListener<SignPatrolBean>, ISignOutListener, IPunchCardListener
        , IPatrolRecordListener<PatrolRecordBean>, PointListListener<PointBean> {

    private PatrolSignInBiz requestBiz;

    public PatrolSignInPresenter() {
        requestBiz = new PatrolSignInBizIml(MyApplicaption.getInstance());
    }

    public void getSystemTime(Map<String, String> map) {
        if (null != mView) {
            if (null != PatrolConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("token", PatrolConfig.getTokenBean().getToken());
                requestBiz.getSystemTime(map, this);
            } else {
                mView.onError("登录失效请重新登录！");
            }
        }
    }

    public void signPatrol(Map<String, String> map) {
        if (null != mView) {
            if (null != PatrolConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("token", PatrolConfig.getTokenBean().getToken());
                requestBiz.signPatrol(map, this);
            } else {
                mView.onError("登录失效请重新登录！");
            }
        }
    }

    public void signOutPatrol(Map<String, String> map) {
        if (null != mView) {
            if (null != PatrolConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("token", PatrolConfig.getTokenBean().getToken());
                requestBiz.signOutPatrol(map, this);
            } else {
                mView.onError("登录失效请重新登录！");
            }
        }
    }

    public void patrolPunchCard(Map<String, String> map) {
        if (null != mView) {
            if (null != PatrolConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("token", PatrolConfig.getTokenBean().getToken());
                requestBiz.patrolPunchCard(map, this);
            } else {
                mView.onError("登录失效请重新登录！");
            }
        }
    }

    public void getPatrolRecord(Map<String, String> map) {
        if (null != mView) {
            if (null != PatrolConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("token", PatrolConfig.getTokenBean().getToken());
                requestBiz.getPatrolRecord(map, this);
            } else {
                mView.onError("登录失效请重新登录！");
            }
        }
    }

// 废弃
//    public void getPointList(Map<String, String> map) {
//        if (null != mView) {
//            if (null != PatrolConfig.getTokenBean().getToken()) {
//                mView.showLoading();
//                map.put("token", PatrolConfig.getTokenBean().getToken());
//                requestBiz.getPointList(map, this);
//            } else {
//                mView.onError("登录失效请重新登录！");
//            }
//        }
//    }

    @Override
    public void onBackSystemTime(SystemTimeBean systemTimeBean) {
        if (null != mView)
            mView.getSystemTimeSuccess(systemTimeBean.getSystemTime());
    }

    @Override
    public void onSignIn(SignPatrolBean signPatrolBean) {
        if (null != mView)
            mView.signInSuccess(signPatrolBean.getSignId());
    }

    @Override
    public void onSignOut() {
        if (null != mView)
            mView.signOutSuccess();
    }

    @Override
    public void onBackPunchCard() {
        if (null != mView)
            mView.patrolPunchCardSuccess();
    }

    @Override
    public void onBackPatrolRecord(PatrolRecordBean patrolRecordBean) {
//        if (null != mView)
//            mView.getPatrolRecordSuccess(patrolRecordBean);
    }


    @Override
    public void onBackPointList(PointBean pointBean) {
//        if (null != mView)
//            mView.getPointListSuccess(pointBean.getListData());
    }

    @Override
    public void onError(String code, String msg) {
        if (null != mView)
            mView.onError(msg);
    }
}

package com.jinke.housekeeper.saas.patrol.view;

import com.jinke.housekeeper.saas.patrol.bean.PatrolRecordBean;
import com.jinke.housekeeper.saas.patrol.bean.PointListBean;

import java.util.List;

/**
 * function:
 * author: hank
 * date: 2017/7/25
 */

public interface PatrolSignInView {

    /**
     * 签到成功
     * @param signId 签到ID
     */
    void signInSuccess(String signId);

    /**
     * 签退成功
     */
    void signOutSuccess();

    /**
     * 打卡成功（废弃）
     */
    void patrolPunchCardSuccess();

//    /**
//     * 打卡记录（废弃）
//     * @param patrolRecordBean 打卡记录列表
//     */
//    void getPatrolRecordSuccess(PatrolRecordBean patrolRecordBean);
//
//    /**
//     * 获取点位信息列表（废弃）
//     * @param pointListBeen 点位信息列表
//     */
//    void getPointListSuccess(List<PointListBean> pointListBeen);

    /**
     * 获取系统时间
     * @param systemTime 系统时间
     */
    void getSystemTimeSuccess(String systemTime);

    void showLoading();

    void onError(String msg);
}

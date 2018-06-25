package com.jinke.housekeeper.saas.patrol.service;

import com.jinke.housekeeper.saas.patrol.listener.IPatrolRecordListener;
import com.jinke.housekeeper.saas.patrol.listener.IPunchCardListener;
import com.jinke.housekeeper.saas.patrol.listener.ISignInListener;
import com.jinke.housekeeper.saas.patrol.listener.ISignOutListener;
import com.jinke.housekeeper.saas.patrol.listener.ISystemTimeListener;
import com.jinke.housekeeper.saas.patrol.listener.PointListListener;

import java.util.Map;

/**
 * function: 签到接口
 * author: hank
 * date: 2017/7/25
 */

public interface PatrolSignInBiz {

    void getSystemTime(Map<String, String> map, ISystemTimeListener listener);

    void signPatrol(Map<String, String> map, ISignInListener listener);

    void signOutPatrol(Map<String, String> map, ISignOutListener listener);

    void patrolPunchCard(Map<String, String> map, IPunchCardListener listener);

    void getPatrolRecord(Map<String, String> map, IPatrolRecordListener listener);

    void getPointList(Map<String, String> map, PointListListener listener);

}

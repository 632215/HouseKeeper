package com.jinke.housekeeper.saas.patrol.http;


import com.jinke.housekeeper.saas.patrol.bean.ContrastDataBean;
import com.jinke.housekeeper.saas.patrol.bean.EmptyBean;
import com.jinke.housekeeper.saas.patrol.bean.HttpResult;
import com.jinke.housekeeper.saas.patrol.bean.IsStartBean;
import com.jinke.housekeeper.saas.patrol.bean.PatrolRecordBean;
import com.jinke.housekeeper.saas.patrol.bean.PointBean;
import com.jinke.housekeeper.saas.patrol.bean.PointDataBean;
import com.jinke.housekeeper.saas.patrol.bean.PointPlanBean;
import com.jinke.housekeeper.saas.patrol.bean.PointProjectDataBean;
import com.jinke.housekeeper.saas.patrol.bean.RemindTimeBean;
import com.jinke.housekeeper.saas.patrol.bean.SignPatrolBean;
import com.jinke.housekeeper.saas.patrol.bean.SignPatrolListBean;
import com.jinke.housekeeper.saas.patrol.bean.SystemTimeBean;
import com.jinke.housekeeper.saas.patrol.bean.TimeDataBean;
import com.jinke.housekeeper.saas.patrol.bean.TimeOutTaskListBean;
import com.jinke.housekeeper.saas.patrol.bean.TokenBean;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by liukun on 16/3/9.
 */
public class HttpMethods {
    private Api api;
    private AppService service;

    //构造方法私有
    private HttpMethods() {
        api = Api.getInstance();
        service = api.getService();
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取token以及用户信息
     *
     * @param subscriber
     * @param map
     */
    public void getToken(Subscriber<HttpResult<TokenBean>> subscriber, Map<String, String> map) {
        Observable observable = service.getToken(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 获取点位信息列表
     *
     * @param subscriber
     * @param map
     */
    public void getPointList(Subscriber<HttpResult<PointBean>> subscriber, Map<String, String> map) {
        Observable observable = service.getPointList(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 获取任务状态
     *
     * @param subscriber
     * @param map
     */
    public void getTimeOutTask(Subscriber<HttpResult<List<TimeOutTaskListBean>>> subscriber, Map<String, String> map) {
        Observable observable = service.getTimeOutTask(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 添加点位
     *
     * @param subscriber
     * @param map
     */
    public void addPoint(Subscriber<HttpResult<EmptyBean>> subscriber, Map<String, String> map) {
        Observable observable = service.addPoint(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 签到接口
     *
     * @param subscriber
     * @param map
     */
    public void signPatrol(Subscriber<HttpResult<SignPatrolBean>> subscriber, Map<String, String> map) {
        Observable observable = service.signPatrol(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 签退接口
     *
     * @param subscriber
     * @param map
     */
    public void signOutPatrol(Subscriber<HttpResult<EmptyBean>> subscriber, Map<String, String> map) {
        Observable observable = service.signOutPatrol(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 提交打卡信息
     *
     * @param subscriber
     * @param map
     */
    public void patrolPunchCard(Subscriber<HttpResult<EmptyBean>> subscriber, Map<String, String> map) {
        Observable observable = service.patrolPunchCard(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 获取打卡记录
     *
     * @param subscriber
     * @param map
     */
    public void getPatrolRecord(Subscriber<HttpResult<PatrolRecordBean>> subscriber, Map<String, String> map) {
        Observable observable = service.getPatrolRecord(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 获取系统时间
     *
     * @param subscriber
     * @param map
     */
    public void getSystemTime(Subscriber<HttpResult<SystemTimeBean>> subscriber, Map<String, String> map) {
        Observable observable = service.getSystemTime(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 获取未签到记录
     *
     * @param subscriber
     * @param map
     */
    public void getIfSignOut(Subscriber<HttpResult<SignPatrolListBean>> subscriber, Map<String, String> map) {
        Observable observable = service.getIfSignOut(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 删除点位
     *
     * @param subscriber
     * @param map
     */
    public void delPoint(Subscriber<HttpResult<EmptyBean>> subscriber, Map<String, String> map) {
        Observable observable = service.delPoint(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 获取闹钟列表
     *
     * @param subscriber
     * @param map
     */
    public void getRemindTime(Subscriber<HttpResult<RemindTimeBean>> subscriber, Map<String, String> map) {
        Observable observable = service.getRemindTime(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 替换点位
     *
     * @param subscriber
     * @param map
     */
    public void replacePoint(Subscriber<HttpResult<EmptyBean>> subscriber, Map<String, String> map) {
        Observable observable = service.replacePoint(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 获取任务列表
     *
     * @param subscriber
     * @param map
     */
    public void getTaskInfo(Subscriber<HttpResult<PointPlanBean>> subscriber, Map<String, String> map) {
        Observable observable = service.getTaskInfo(map);
        api.toSubscribe(observable, subscriber);
    }
    /**
     * 点击任务获取点位
     *
     * @param subscriber
     * @param map
     */
    public void isStart(Subscriber<HttpResult<IsStartBean>> subscriber, Map<String, String> map) {
        Observable observable = service.isStart(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 点击任务获取点位
     *
     * @param subscriber
     * @param map
     */
    public void pointData(Subscriber<HttpResult<List<PointDataBean>>> subscriber, Map<String, String> map) {
        Observable observable = service.pointData(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 点击任务获取点位
     *
     * @param subscriber
     * @param map
     */
    public void timeData(Subscriber<HttpResult<List<TimeDataBean>>> subscriber, Map<String, String> map) {
        Observable observable = service.timeData(map);
        api.toSubscribe(observable, subscriber);
    }


    /**
     * 点击任务获取点位
     *
     * @param subscriber
     * @param map
     */
    public void pointProjectData(Subscriber<HttpResult<List<PointProjectDataBean>>> subscriber, Map<String, String> map) {
        Observable observable = service.pointProjectData(map);
        api.toSubscribe(observable, subscriber);
    }


    /**
     * 点击任务获取点位
     *
     * @param subscriber
     * @param map
     */
    public void contrastData(Subscriber<HttpResult<List<ContrastDataBean>>> subscriber, Map<String, String> map) {
        Observable observable = service.contrastData(map);
        api.toSubscribe(observable, subscriber);
    }


}

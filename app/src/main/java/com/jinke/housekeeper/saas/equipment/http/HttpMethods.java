package com.jinke.housekeeper.saas.equipment.http;


import com.jinke.housekeeper.saas.equipment.bean.AddPointCallBackBean;
import com.jinke.housekeeper.saas.equipment.bean.DailyPatrolBean;
import com.jinke.housekeeper.saas.equipment.bean.DeviceInfoBean;
import com.jinke.housekeeper.saas.equipment.bean.DeviceTypeBean;
import com.jinke.housekeeper.saas.equipment.bean.ElectricDataBean;
import com.jinke.housekeeper.saas.equipment.bean.ElectricMonthData;
import com.jinke.housekeeper.saas.equipment.bean.EmptyBean;
import com.jinke.housekeeper.saas.equipment.bean.HttpResult;
import com.jinke.housekeeper.saas.equipment.bean.NoPointBean;
import com.jinke.housekeeper.saas.equipment.bean.ParameterBean;
import com.jinke.housekeeper.saas.equipment.bean.ReadWatchBean;
import com.jinke.housekeeper.saas.equipment.bean.TaskListBean;
import com.jinke.housekeeper.saas.equipment.bean.TokenBean;

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
     * 获取是否有未添加的设备
     *
     * @param subscriber
     * @param map
     */
    public void getNoPoint(Subscriber<HttpResult<NoPointBean>> subscriber, Map<String, String> map) {
        Observable observable = service.getNoPoint(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 获取设备类型
     *
     * @param subscriber
     * @param map
     */
    public void getDeviceType(Subscriber<HttpResult<DeviceTypeBean>> subscriber, Map<String, String> map) {
        Observable observable = service.getDeviceType(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 获取设备详情
     *
     * @param subscriber
     * @param map
     */
    public void getDeviceInfo(Subscriber<HttpResult<DeviceInfoBean>> subscriber, Map<String, String> map) {
        Observable observable = service.getDeviceInfo(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 绑定巡检设备
     *
     * @param subscriber
     * @param map
     */
    public void getAddPoint(Subscriber<HttpResult<AddPointCallBackBean>> subscriber, Map<String, String> map) {
        Observable observable = service.getAddPoint(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 获取巡检统计数据
     *
     * @param subscriber
     * @param map
     */
    public void getStatistics(Subscriber<HttpResult<DailyPatrolBean>> subscriber, Map<String, String> map) {
        Observable observable = service.getStatistics(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 获取当前部门下的所有任务
     *
     * @param subscriber
     * @param map
     */
    public void getTask(Subscriber<HttpResult<TaskListBean>> subscriber, Map<String, String> map) {
        Observable observable = service.getTask(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 上传完成的任务
     *
     * @param subscriber
     * @param map
     */
    public void completeTask(Subscriber<HttpResult<EmptyBean>> subscriber, Map<String, String> map) {
        Observable observable = service.completeTask(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 三表列表
     *
     * @param subscriber
     * @param map
     */
    public void readWatch(Subscriber<HttpResult<ReadWatchBean>> subscriber, Map<String, String> map) {
        Observable observable = service.readWatch(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 三表详情
     *
     * @param subscriber
     * @param map
     */
    public void getParameter(Subscriber<HttpResult<ParameterBean>> subscriber, Map<String, String> map) {
        Observable observable = service.getParameter(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 上传抄写三表信息
     *
     * @param subscriber
     * @param map
     */
    public void uploadData(Subscriber<HttpResult<EmptyBean>> subscriber, Map<String, String> map) {
        Observable observable = service.uploadData(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 获取每日统计表
     *
     * @param subscriber
     * @param map
     */
    public void getElectricData(Subscriber<HttpResult<ElectricDataBean>> subscriber, Map<String, String> map) {
        Observable observable = service.getElectricData(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 获取每月统计表
     *
     * @param subscriber
     * @param map
     */
    public void getElectricMonthData(Subscriber<HttpResult<ElectricMonthData>> subscriber, Map<String, String> map) {
        Observable observable = service.getElectricMonthData(map);
        api.toSubscribe(observable, subscriber);
    }

}

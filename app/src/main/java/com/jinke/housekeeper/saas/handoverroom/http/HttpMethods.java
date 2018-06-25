package com.jinke.housekeeper.saas.handoverroom.http;


import com.jinke.housekeeper.saas.handoverroom.bean.EmptyBean;
import com.jinke.housekeeper.saas.handoverroom.bean.FindListDataBean;
import com.jinke.housekeeper.saas.handoverroom.bean.FindStateBean;
import com.jinke.housekeeper.saas.handoverroom.bean.HandoverRoomBean;
import com.jinke.housekeeper.saas.handoverroom.bean.HttpResult;
import com.jinke.housekeeper.saas.handoverroom.bean.TokenBean;

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
    public void getUserToken(Subscriber<HttpResult<TokenBean>> subscriber, Map<String, String> map) {
        Observable observable = service.getUserToken(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 获取项目、设备列表接口
     *
     * @param subscriber
     * @param map
     */
    public void findListData(Subscriber<HttpResult<FindListDataBean>> subscriber, Map<String, String> map) {
        Observable observable = service.findListData(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 设备绑定接口
     *
     * @param subscriber
     * @param map
     */
    public void bindingDevice(Subscriber<HttpResult<EmptyBean>> subscriber, Map<String, String> map) {
        Observable observable = service.bindingDevice(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 获取当前接房用户的信息
     *
     * @param subscriber
     * @param map
     */
    public void getHouseInfo(Subscriber<HttpResult<HandoverRoomBean>> subscriber, Map<String, String> map) {
        Observable observable = service.getHouseInfo(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 查询用户接房状态接口
     *
     * @param subscriber
     * @param map
     */
    public void findState(Subscriber<HttpResult<FindStateBean>> subscriber, Map<String, String> map) {
        Observable observable = service.findState(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 开始接房流程
     *
     * @param subscriber
     * @param map
     */
    public void handleHouse(Subscriber<HttpResult<EmptyBean>> subscriber, Map<String, String> map) {
        Observable observable = service.handleHouse(map);
        api.toSubscribe(observable, subscriber);
    }

    /**
     * 结束接房流程
     *
     * @param subscriber
     * @param map
     */
    public void takeHouse(Subscriber<HttpResult<EmptyBean>> subscriber, Map<String, String> map) {
        Observable observable = service.takeHouse(map);
        api.toSubscribe(observable, subscriber);
    }

}

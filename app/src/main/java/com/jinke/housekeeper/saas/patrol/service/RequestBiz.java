package com.jinke.housekeeper.saas.patrol.service;

import com.jinke.housekeeper.saas.patrol.listener.OnRequestListener;
import com.jinke.housekeeper.saas.patrol.listener.IAddPointListener;

import java.util.Map;

public interface RequestBiz {
    /**
     * 获取token以及用户信息
     *
     * @param map
     * @param listener
     */
    void requestForData(Map<String, String> map, OnRequestListener listener);

    /**
     * 获取巡更点信息
     *
     * @param map
     * @param listener
     */
    void getPointList(Map<String, String> map, IAddPointListener.OnGetPoinListListener listener);

}
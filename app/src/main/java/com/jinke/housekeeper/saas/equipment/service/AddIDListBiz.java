package com.jinke.housekeeper.saas.equipment.service;

import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestListener;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/10/10
 */

public interface AddIDListBiz {
    /**
     * 获取token以及用户信息
     *
     * @param map
     * @param listener
     */
    void getAddPoint(Map<String, String> map, OnRequestListener listener);
}

package com.jinke.housekeeper.saas.equipment.service;

import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestListener;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/9/30
 */

public interface BaseInfoBiz {

    /**
     * 获取设备详情
     *
     * @param map
     * @param listener
     */
    void getDeviceInfo(Map<String, String> map, OnRequestListener listener);
}

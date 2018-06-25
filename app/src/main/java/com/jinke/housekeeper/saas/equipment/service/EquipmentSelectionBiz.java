package com.jinke.housekeeper.saas.equipment.service;

import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestListener;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/9/29
 */

public interface EquipmentSelectionBiz {

    /**
     * 获取设备
     *
     * @param map
     * @param listener
     */
    void getDeviceType(Map<String, String> map, OnRequestListener listener);

}

package com.jinke.housekeeper.saas.handoverroom.service;

import com.jinke.housekeeper.saas.handoverroom.http.listener.OnBindingDeviceRequestListener;
import com.jinke.housekeeper.saas.handoverroom.http.listener.OnRequestListener;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/12/1
 */

public interface EquipmentConfigurationBiz {

    /**
     * 获取项目、设备列表接口
     *
     * @param map
     * @param listener
     */
    void findListData(Map<String, String> map, OnRequestListener listener);

    /**
     * 设备绑定接口
     *
     * @param map
     * @param listener
     */
    void bindingDevice(Map<String, String> map, OnBindingDeviceRequestListener listener);
}

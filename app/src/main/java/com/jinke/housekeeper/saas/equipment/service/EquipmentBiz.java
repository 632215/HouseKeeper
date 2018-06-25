package com.jinke.housekeeper.saas.equipment.service;


import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestListener;
import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestNoPointListener;

import java.util.Map;

/**
 * function: 数据桥梁处理接口
 * author: hank
 * date: 2017/9/20
 */

public interface EquipmentBiz {
    /**
     * 获取token以及用户信息
     *
     * @param map
     * @param listener
     */
    void requestForTokenData(Map<String, String> map, OnRequestListener listener);

    /**
     * 获取是否有为添加的设备
     *
     * @param map
     * @param listener
     */
    void requestForNoPointData(Map<String, String> map, OnRequestNoPointListener listener);
}

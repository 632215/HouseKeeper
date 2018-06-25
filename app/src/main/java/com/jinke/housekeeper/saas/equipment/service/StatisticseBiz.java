package com.jinke.housekeeper.saas.equipment.service;

import com.jinke.housekeeper.saas.equipment.http.listener.GetElectricMonthDataOnRequestListener;
import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestListener;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/11/18
 */

public interface StatisticseBiz {
    /**
     * 获取三表列表
     *
     * @param map
     * @param listener
     */
    void getElectricData(Map<String, String> map, OnRequestListener listener);

    /**
     * 获取三表详情
     *
     * @param map
     * @param listener
     */
    void getElectricMonthData(Map<String, String> map, GetElectricMonthDataOnRequestListener listener);

}

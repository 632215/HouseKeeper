package com.jinke.housekeeper.saas.equipment.service;

import com.jinke.housekeeper.saas.equipment.http.listener.CompleteTaskOnRequestListener;
import com.jinke.housekeeper.saas.equipment.http.listener.GetParameterOnRequestListener;
import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestListener;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/11/11
 */

public interface ThreeTableListBiz {
    /**
     * 获取三表列表
     *
     * @param map
     * @param listener
     */
    void readWatch(Map<String, String> map, OnRequestListener listener);

    /**
     * 获取三表详情
     *
     * @param map
     * @param listener
     */
    void getParameter(Map<String, String> map, GetParameterOnRequestListener listener);

    /**
     * 上传三表
     *
     * @param map
     * @param listener
     */
    void uploadData(Map<String, String> map, CompleteTaskOnRequestListener listener);
}

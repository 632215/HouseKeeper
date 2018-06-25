package com.jinke.housekeeper.saas.patrol.service;

import com.jinke.housekeeper.saas.patrol.listener.OnRequestListener;
import com.jinke.housekeeper.saas.patrol.listener.PointPlanListener;

import java.util.Map;

/**
 * author : huominghao
 * date : 2018/1/25 0025
 * function :
 */

public interface PointPlanBiz {

    /**
     * 获取任务列表
     *
     * @param map
     * @param listener
     */
    void  getTaskInfo(Map<String, String> map, OnRequestListener listener);

    /**
     * 获取任务列表
     *
     * @param map
     * @param listener
     */
    void  isStart(Map<String, String> map, PointPlanListener listener);
}

package com.jinke.housekeeper.saas.equipment.service;

import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestCompleteTaskListener;
import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestListener;
import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestTaskListener;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/10/11
 */

public interface DailyPatrolBiz {

    /**
     * 获取巡检统计数据
     *
     * @param map
     * @param listener
     */
    void getStatistics(Map<String, String> map, OnRequestListener listener);

    /**
     * 获取当前部门下的所有任务
     *
     * @param map
     * @param listener
     */
    void getTask(Map<String, String> map, OnRequestTaskListener listener);

    /**
     * 获取当前部门下的所有任务
     *
     * @param map
     * @param listener
     */
    void completeTask(Map<String, String> map, OnRequestCompleteTaskListener listener);
}

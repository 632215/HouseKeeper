package com.jinke.housekeeper.saas.patrol.service;

import com.jinke.housekeeper.saas.patrol.listener.OnRequestListener;

import java.util.Map;

/**
 * author : huominghao
 * date : 2018/3/4 0004
 * function :
 */

public interface PatrolStatCaleBiz {
    /**
     * 日历报表
     *
     * @param map
     * @param listener
     */
    void timeData(Map<String, String> map, OnRequestListener listener);
}

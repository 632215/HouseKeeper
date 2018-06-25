package com.jinke.housekeeper.saas.patrol.service;

import com.jinke.housekeeper.saas.patrol.listener.OnRequestListener;

import java.util.Map;

/**
 * author : huominghao
 * date : 2018/3/2 0002
 * function :
 */

public interface StatementBiz {
    /**
     * 对比报表数据主数据
     *
     * @param map
     * @param listener
     */
    void pointData(Map<String, String> map, OnRequestListener listener);

}

package com.jinke.housekeeper.saas.patrol.service;

import com.jinke.housekeeper.saas.patrol.listener.ContrastRequestListener;
import com.jinke.housekeeper.saas.patrol.listener.OnRequestListener;

import java.util.Map;

/**
 * author : huominghao
 * date : 2018/3/4 0004
 * function :
 */

public interface PatrolStatementBiz {
    /**
     * @param map
     * @param listener
     */
    void pointProjectData(Map<String, String> map, OnRequestListener listener);


    /**
     * @param map
     * @param listener
     */
    void contrastData(Map<String, String> map, ContrastRequestListener listener);



}

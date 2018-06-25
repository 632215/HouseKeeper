package com.jinke.housekeeper.saas.patrol.service;

import com.jinke.housekeeper.saas.patrol.listener.OnDeletePointListener;
import com.jinke.housekeeper.saas.patrol.listener.OnRequestListener;
import com.jinke.housekeeper.saas.patrol.listener.OnTimeOutTaskListener;

import java.util.Map;

/**
 * Created by root on 17-7-24.
 */

public interface PatrolManageBiz {
    void getPointList(Map<String, String> map, OnRequestListener listener);

    void getTimeOutTask(Map<String, String> map, OnTimeOutTaskListener listener);

    void delPoint(Map<String, String> map, OnDeletePointListener listener);
}

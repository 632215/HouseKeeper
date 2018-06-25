package com.jinke.housekeeper.saas.patrol.service;

import com.jinke.housekeeper.saas.patrol.listener.OnRequestListener;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/8/3
 */

public interface RemindTimeBiz {
    void getRemindTime(Map<String, String> map, OnRequestListener listener);
}

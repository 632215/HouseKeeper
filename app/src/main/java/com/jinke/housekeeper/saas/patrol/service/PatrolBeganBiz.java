package com.jinke.housekeeper.saas.patrol.service;

import com.jinke.housekeeper.saas.patrol.listener.OnRequestListener;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/7/28
 */

public interface PatrolBeganBiz {
    void getIfSignOut(Map<String, String> map, OnRequestListener listener);
}

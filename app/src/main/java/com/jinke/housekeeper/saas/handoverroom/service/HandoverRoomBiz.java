package com.jinke.housekeeper.saas.handoverroom.service;

import com.jinke.housekeeper.saas.handoverroom.http.listener.OnRequestListener;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/11/26
 */

public interface HandoverRoomBiz {
    /**
     * 获取Token
     *
     * @param map
     * @param listener
     */
    void getUserToken(Map<String, String> map, OnRequestListener listener);
}

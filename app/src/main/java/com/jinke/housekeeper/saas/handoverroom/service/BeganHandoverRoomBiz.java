package com.jinke.housekeeper.saas.handoverroom.service;

import com.jinke.housekeeper.saas.handoverroom.http.listener.OnFindStateRequestListener;
import com.jinke.housekeeper.saas.handoverroom.http.listener.OnGetHouseInfoRequestListener;
import com.jinke.housekeeper.saas.handoverroom.http.listener.OnRequestListener;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/11/26
 */

public interface BeganHandoverRoomBiz {
    /**
     * 开始接房流程
     *
     * @param map
     * @param listener
     */
    void handleHouse(Map<String, String> map, OnRequestListener listener);

    /**
     * 获取当前接房用户的信息
     *
     * @param map
     * @param listener
     */
    void getHouseInfo(Map<String, String> map, OnGetHouseInfoRequestListener listener);

    /**
     * 查询用户接房状态接口
     *
     * @param map
     * @param listener
     */
    void findState(Map<String, String> map, OnFindStateRequestListener listener);
}

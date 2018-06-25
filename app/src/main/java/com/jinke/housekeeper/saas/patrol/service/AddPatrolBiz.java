package com.jinke.housekeeper.saas.patrol.service;

import com.jinke.housekeeper.saas.patrol.listener.IAddPointListener;

import java.util.Map;

/**
 * Created by root on 17-7-24.
 */

public interface AddPatrolBiz {
    void addPoint(Map<String, String> map, IAddPointListener listener);
}

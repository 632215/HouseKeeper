package com.jinke.housekeeper.saas.report.service;

import com.jinke.housekeeper.saas.report.service.listener.MapActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface MapActivityBiz {
    void getMapPoint(SortedMap<String, String> map, MapActivityListener listener);
}

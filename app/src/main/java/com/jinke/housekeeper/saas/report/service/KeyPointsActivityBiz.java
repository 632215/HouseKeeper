package com.jinke.housekeeper.saas.report.service;

import com.jinke.housekeeper.saas.report.service.listener.KeyPointsActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface KeyPointsActivityBiz {

    void getGJContentData(SortedMap<String, String> map, KeyPointsActivityListener listener);
}

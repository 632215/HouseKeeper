package com.jinke.housekeeper.saas.report.service;

import com.jinke.housekeeper.service.listener.WorkBenchFragmentListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/6.
 */

public interface WorkBenchFragmentBiz {
    void getMapPoint(SortedMap<String, String> map, WorkBenchFragmentListener listener);
}

package com.jinke.housekeeper.saas.report.service;

import com.jinke.housekeeper.saas.report.service.listener.FoundProblemActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface FoundProblemActivityBiz {
    void getProcessDetail(SortedMap<String, String> map, FoundProblemActivityListener listener);

    void grabSingle(SortedMap<String, String> map, FoundProblemActivityListener listener);
}

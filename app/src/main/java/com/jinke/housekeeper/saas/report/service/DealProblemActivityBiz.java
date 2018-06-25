package com.jinke.housekeeper.saas.report.service;

import com.jinke.housekeeper.saas.report.service.listener.DealProblemActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface DealProblemActivityBiz {
    void getProcessDetail(SortedMap<String, String> map, DealProblemActivityListener listener);
}

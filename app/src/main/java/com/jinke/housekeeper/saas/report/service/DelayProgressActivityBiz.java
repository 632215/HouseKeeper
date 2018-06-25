package com.jinke.housekeeper.saas.report.service;

import com.jinke.housekeeper.saas.report.service.listener.DelayProgressActivityViewListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface DelayProgressActivityBiz {
    void getProcessDetail(SortedMap<String, String> map, DelayProgressActivityViewListener listener);
}

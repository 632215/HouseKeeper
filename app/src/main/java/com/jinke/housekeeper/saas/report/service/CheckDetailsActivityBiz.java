package com.jinke.housekeeper.saas.report.service;

import com.jinke.housekeeper.saas.report.service.listener.CheckDetailsActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface CheckDetailsActivityBiz {
    void getProcessDetail(SortedMap<String, String> map, CheckDetailsActivityListener listener);
}

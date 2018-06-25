package com.jinke.housekeeper.saas.report.service;


import com.jinke.housekeeper.saas.report.service.listener.ProcessDetailActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface ProcessDetailActivityBiz {
    void getWorkDetail(SortedMap<String, String> map, ProcessDetailActivityListener listener);

    void getProcessDetail(SortedMap<String, String> map, ProcessDetailActivityListener listener);
}

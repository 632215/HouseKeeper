package com.jinke.housekeeper.saas.report.service;

import com.jinke.housekeeper.saas.report.service.listener.GrabSingleFragmentListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface GrabSingleFragmentBiz {
    void initDate(SortedMap<String, String> map, GrabSingleFragmentListener listener);
}

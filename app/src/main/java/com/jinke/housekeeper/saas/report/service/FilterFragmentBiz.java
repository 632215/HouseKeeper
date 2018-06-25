package com.jinke.housekeeper.saas.report.service;

import com.jinke.housekeeper.saas.report.service.listener.FilterFragmentListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface FilterFragmentBiz {
    void getRectifiedList(SortedMap<String, String> map, FilterFragmentListener listener);
}

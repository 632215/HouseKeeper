package com.jinke.housekeeper.saas.report.service;

import com.jinke.housekeeper.saas.report.service.listener.PersonFilterFragmentListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface PersonFilterFragmentBiz {
    void getSelfHistoryList(SortedMap<String, String> map, PersonFilterFragmentListener listener);
}

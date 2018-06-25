package com.jinke.housekeeper.saas.report.service;

import com.jinke.housekeeper.saas.report.service.listener.PersonTodayFragmentListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface PersonTodayFragmentBiz {
    void getSelfHistoryList(SortedMap<String, String> map, PersonTodayFragmentListener listener);
}

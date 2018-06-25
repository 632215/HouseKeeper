package com.jinke.housekeeper.saas.report.service;

import com.jinke.housekeeper.saas.report.service.listener.PersonYesterdayFragmentListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface PersonYesterdayFragmentBiz {
    void getSelfHistoryList(SortedMap<String, String> map, PersonYesterdayFragmentListener listener);
}

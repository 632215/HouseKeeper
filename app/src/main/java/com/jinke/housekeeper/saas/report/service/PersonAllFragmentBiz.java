package com.jinke.housekeeper.saas.report.service;

import com.jinke.housekeeper.saas.report.service.listener.PersonAllFragmentListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface PersonAllFragmentBiz {
    void getSelfHistoryList(SortedMap<String, String> map, PersonAllFragmentListener listener);
}

package com.jinke.housekeeper.saas.report.service;

import com.jinke.housekeeper.saas.report.service.listener.ContactsActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface ContactsActivityBiz {
    void getUserListData(SortedMap<String, String> map, ContactsActivityListener listener);
}

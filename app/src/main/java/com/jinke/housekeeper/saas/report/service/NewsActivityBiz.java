package com.jinke.housekeeper.saas.report.service;

import com.jinke.housekeeper.saas.report.service.listener.NewsActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface NewsActivityBiz {
    void updateReadStatus(SortedMap<String, String> map, NewsActivityListener listener);
}

package com.jinke.housekeeper.saas.report.service;

import com.jinke.housekeeper.saas.report.service.listener.NewsFragmentListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface NewsFragmentBiz {
    void initMagData(SortedMap<String, String> map, NewsFragmentListener listener);

    void updateReadStatus(SortedMap<String, String> map, NewsFragmentListener listener, int p);
}

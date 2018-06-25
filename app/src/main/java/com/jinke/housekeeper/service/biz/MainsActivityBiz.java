package com.jinke.housekeeper.service.biz;

import com.jinke.housekeeper.service.listener.MainsActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/15.
 */

public interface MainsActivityBiz {
    void getAllScenePage(SortedMap<String, String> map, MainsActivityListener listener);
}

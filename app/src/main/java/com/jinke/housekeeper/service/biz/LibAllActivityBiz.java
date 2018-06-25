package com.jinke.housekeeper.service.biz;

import com.jinke.housekeeper.service.listener.LibAllActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface LibAllActivityBiz {
    void getScenePage(SortedMap<String, String> map, LibAllActivityListener listener);
}

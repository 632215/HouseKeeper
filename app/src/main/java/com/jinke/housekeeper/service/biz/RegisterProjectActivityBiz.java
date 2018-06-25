package com.jinke.housekeeper.service.biz;

import com.jinke.housekeeper.service.listener.RegisterProjectActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/15.
 */

public interface RegisterProjectActivityBiz {
    void getXMList(SortedMap<String, String> map, RegisterProjectActivityListener listener);
}

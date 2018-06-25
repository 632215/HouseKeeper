package com.jinke.housekeeper.service.biz;

import com.jinke.housekeeper.service.listener.RegisterActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/15.
 */

public interface RegisterActivityBiz {
    void getRegisterData(SortedMap<String, String> map, RegisterActivityListener listener);
}

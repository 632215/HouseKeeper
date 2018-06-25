package com.jinke.housekeeper.service.biz;

import com.jinke.housekeeper.service.listener.LoginActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/15.
 */

public interface LoginActivityBiz {
    void getUserLoginData(SortedMap<String, String> map, LoginActivityListener listener);
}

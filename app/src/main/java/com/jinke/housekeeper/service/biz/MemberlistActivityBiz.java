package com.jinke.housekeeper.service.biz;

import com.jinke.housekeeper.service.listener.MemberlistActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface MemberlistActivityBiz {
    void getMenberList(SortedMap<String, String> map, MemberlistActivityListener listener, boolean isShow);
}

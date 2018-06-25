package com.jinke.housekeeper.service.biz;

import com.jinke.housekeeper.service.listener.MemberDetailActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface MemberDetailActivityBiz {
    void getUserDelete(SortedMap<String, String> map, MemberDetailActivityListener listener);
}

package com.jinke.housekeeper.service.biz;

import com.jinke.housekeeper.service.listener.LibDetailsActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface LibDetailsActivityBiz {
    void getDetailsData(SortedMap<String, String> map, LibDetailsActivityListener listener);
}

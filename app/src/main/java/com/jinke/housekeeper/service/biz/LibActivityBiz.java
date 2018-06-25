package com.jinke.housekeeper.service.biz;

import com.jinke.housekeeper.service.listener.LibActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface LibActivityBiz {
    void getLore(SortedMap<String, String> map, LibActivityListener listener);
}

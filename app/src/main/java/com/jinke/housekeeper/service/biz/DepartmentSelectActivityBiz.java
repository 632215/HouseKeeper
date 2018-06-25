package com.jinke.housekeeper.service.biz;

import com.jinke.housekeeper.service.listener.DepartmentSelectActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface DepartmentSelectActivityBiz {
    void getCJContentData(SortedMap<String, String> map, DepartmentSelectActivityListener listener);
}

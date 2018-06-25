package com.jinke.housekeeper.service.biz;

import com.jinke.housekeeper.service.listener.RegisterDepartmentActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/15.
 */

public interface RegisterDepartmentActivityBiz {
    void getCJContentData(SortedMap<String, String> map, RegisterDepartmentActivityListener listener);
}

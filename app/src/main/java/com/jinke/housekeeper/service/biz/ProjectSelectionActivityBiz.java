package com.jinke.housekeeper.service.biz;

import com.jinke.housekeeper.service.listener.ProjectSelectionActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface ProjectSelectionActivityBiz {
    void getCountXMList(SortedMap<String, String> map, ProjectSelectionActivityListener listener);
}

package com.jinke.housekeeper.service.biz;

import com.jinke.housekeeper.service.listener.ProjectSFragmentListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface ProjectSFragmentBiz {
    void getChangTotal(SortedMap<String, String> map, ProjectSFragmentListener listener);

    void getStatistics(SortedMap<String, String> map, ProjectSFragmentListener listener);
    void getStatisticst(SortedMap<String, String> map, ProjectSFragmentListener listener);
}

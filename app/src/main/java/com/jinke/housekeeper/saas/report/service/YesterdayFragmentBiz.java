package com.jinke.housekeeper.saas.report.service;

import com.jinke.housekeeper.saas.report.service.listener.AllFragmentListener;
import com.jinke.housekeeper.saas.report.service.listener.YesterdayFragmentListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface YesterdayFragmentBiz {
    void getRectifiedList(SortedMap<String, String> map, YesterdayFragmentListener listener);
}

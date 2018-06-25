package com.jinke.housekeeper.saas.report.service;

import com.jinke.housekeeper.saas.report.service.listener.InquireYesterdayFragmentListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface InquireYesterdayFragmentBiz {
    void getAllReportList(SortedMap<String, String> map, InquireYesterdayFragmentListener listener);
}

package com.jinke.housekeeper.saas.report.service;

import com.jinke.housekeeper.saas.report.service.listener.InquireAllFragmentListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface InquireAllFragmentBiz {
    void getAllReportList(SortedMap<String, String> map, InquireAllFragmentListener listener);
}

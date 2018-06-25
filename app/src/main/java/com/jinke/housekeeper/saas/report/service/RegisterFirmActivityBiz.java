package com.jinke.housekeeper.saas.report.service;


import com.jinke.housekeeper.saas.report.service.listener.RegisterFirmActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/15.
 */

public interface RegisterFirmActivityBiz {
    void getXMList(SortedMap<String, String> map, RegisterFirmActivityListener listener);
}

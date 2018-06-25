package com.jinke.housekeeper.saas.report.service.listener;

import com.jinke.housekeeper.saas.report.bean.NodeDelayBean;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface DelayProgressActivityViewListener {
    void getProcessDetailonNext(NodeDelayBean info);

    void getProcessDetailonError(String code, String msg);
}

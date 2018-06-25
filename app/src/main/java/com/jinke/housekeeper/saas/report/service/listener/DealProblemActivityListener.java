package com.jinke.housekeeper.saas.report.service.listener;

import com.jinke.housekeeper.saas.report.bean.NodeDetailBean;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface DealProblemActivityListener {
    void getProcessDetailonError(String code, String msg);

    void getProcessDetailonNext(NodeDetailBean info);
}

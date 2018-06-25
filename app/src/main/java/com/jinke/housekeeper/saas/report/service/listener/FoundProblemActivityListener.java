package com.jinke.housekeeper.saas.report.service.listener;

import com.jinke.housekeeper.saas.report.bean.NodeDetailBean;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface FoundProblemActivityListener {
    void getProcessDetailonNext(NodeDetailBean info);

    void getProcessDetailonError(String code, String msg);

    void grabSingleonNext();

    void grabSingleonError(String code, String msg);
}

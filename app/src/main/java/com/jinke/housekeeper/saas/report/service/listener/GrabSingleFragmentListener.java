package com.jinke.housekeeper.saas.report.service.listener;

import com.jinke.housekeeper.saas.report.bean.WorkOrderBean;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface GrabSingleFragmentListener {
    void initDateOnNext(WorkOrderBean info);

    void initDateOnError(String code, String msg);
}

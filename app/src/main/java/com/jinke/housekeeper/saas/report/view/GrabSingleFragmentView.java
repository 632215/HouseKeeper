package com.jinke.housekeeper.saas.report.view;

import com.jinke.housekeeper.saas.report.bean.WorkOrderBean;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface GrabSingleFragmentView {
    void initDateOnError(String code, String msg);

    void initDateOnNext(WorkOrderBean info);
}

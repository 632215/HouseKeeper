package com.jinke.housekeeper.saas.report.service.listener;

import com.jinke.housekeeper.saas.report.bean.AllReportBean;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface InquireFilterFragmentListener {
    void getAllReportListonError(String code, String msg);

    void getAllReportListonNext(AllReportBean bean);
}

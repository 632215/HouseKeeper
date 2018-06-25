package com.jinke.housekeeper.saas.report.service.listener;

import com.jinke.housekeeper.saas.report.bean.AllReportBean;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface InquireAllFragmentListener {
    void getAllReportListonNext(AllReportBean bean);

    void getAllReportListonErrorbean(String code, String msg);
}

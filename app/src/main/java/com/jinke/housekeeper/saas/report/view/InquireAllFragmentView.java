package com.jinke.housekeeper.saas.report.view;

import com.jinke.housekeeper.saas.report.bean.AllReportBean;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface InquireAllFragmentView {
    void getAllReportListonErrorbean(String code, String msg);

    void getAllReportListonNext(AllReportBean bean);
}

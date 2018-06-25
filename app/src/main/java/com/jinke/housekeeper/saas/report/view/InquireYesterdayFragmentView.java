package com.jinke.housekeeper.saas.report.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.saas.report.bean.AllReportBean;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface InquireYesterdayFragmentView {
    void getAllReportListonError(String code, String msg);

    void getAllReportListonNext(AllReportBean bean);
}

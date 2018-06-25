package com.jinke.housekeeper.saas.report.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.saas.report.bean.AllReportBean;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface InquireTodayFragmentView{
    void getAllReportListonNext(AllReportBean bean);

    void getAllReportListonError(String code, String msg);
}

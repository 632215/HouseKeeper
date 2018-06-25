package com.jinke.housekeeper.saas.report.service.listener;

import com.jinke.housekeeper.bean.RegisterProjectBean;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface ReportRegistActivityListener {
    void upLoadDataResult(String s);

    void showMessage(String errorMsg);
}

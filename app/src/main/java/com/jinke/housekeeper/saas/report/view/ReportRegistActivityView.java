package com.jinke.housekeeper.saas.report.view;

import com.jinke.housekeeper.base.BaseView;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface ReportRegistActivityView extends BaseView{
    void upLoadDataResult(String s);

    void showMessage(String errorMsg);
}

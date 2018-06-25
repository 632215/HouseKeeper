package com.jinke.housekeeper.saas.report.view;

import com.jinke.housekeeper.base.BaseView;

/**
 * Created by Administrator on 2017/9/19.
 */

public interface ReportFragmentView extends BaseView{
    void getUpFileonSuccess();

    void getUpFileonFailure(String errorMsg);
}

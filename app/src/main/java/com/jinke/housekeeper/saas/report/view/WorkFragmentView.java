package com.jinke.housekeeper.saas.report.view;

import com.jinke.housekeeper.base.BaseView;

/**
 * Created by Administrator on 2017/9/20.
 */

public interface WorkFragmentView extends BaseView{
    void getappHandleDataSuccess();

    void getappHandleDataFailure(String errorMsg);
}

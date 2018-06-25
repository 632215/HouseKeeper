package com.jinke.housekeeper.saas.report.view;

import com.jinke.housekeeper.base.BaseView;

/**
 * Created by Administrator on 2017/9/19.
 */

public interface FinishFragmentView extends BaseView{
    void getFileUponSuccess();

    void getFileUponFailure(String errorMsg);
}

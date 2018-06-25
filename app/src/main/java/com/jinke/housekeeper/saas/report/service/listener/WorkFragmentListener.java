package com.jinke.housekeeper.saas.report.service.listener;

/**
 * Created by Administrator on 2017/9/20.
 */

public interface WorkFragmentListener {
    void getappHandleDataSuccess();

    void getappHandleDataFailure(String errorMsg);
}

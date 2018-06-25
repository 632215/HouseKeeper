package com.jinke.housekeeper.saas.report.service.listener;

/**
 * Created by Administrator on 2017/9/19.
 */

public interface ReportFragmentListener {
    void getUpFileonSuccess();

    void getFileUponFailure(String errorMsg);
}

package com.jinke.housekeeper.saas.report.service.listener;

import com.jinke.housekeeper.db.SelfCheckingBean;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface FileUploadingActivityListener {
    void getUpFileonSuccess(SelfCheckingBean selfCheckingBean, String s);

    void getUpFileshowToast(String errorMsg);

    void getUpFileshowonFailure(String msg);
}

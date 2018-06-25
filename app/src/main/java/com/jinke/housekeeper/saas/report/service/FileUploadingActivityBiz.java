package com.jinke.housekeeper.saas.report.service;

import com.jinke.housekeeper.db.SelfCheckingBean;
import com.jinke.housekeeper.saas.report.service.listener.FileUploadingActivityListener;
import com.lidroid.xutils.http.RequestParams;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface FileUploadingActivityBiz {
    void getUpFile(RequestParams params, SelfCheckingBean selfCheckingBean, FileUploadingActivityListener listener);
}

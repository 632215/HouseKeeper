package com.jinke.housekeeper.saas.report.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.db.SelfCheckingBean;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface FileUploadingActivityView extends BaseView{
    void getUpFileonSuccess(SelfCheckingBean selfCheckingBean, String s);

    void getUpFileshowToast(String errorMsg);

    void getUpFileshowonFailure(String msg);
}

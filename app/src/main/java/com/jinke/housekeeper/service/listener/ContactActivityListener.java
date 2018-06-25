package com.jinke.housekeeper.service.listener;

import com.jinke.housekeeper.bean.MailListBean;

/**
 * Created by hank on 2017/12/18 0018.
 */

public interface ContactActivityListener {

    void getMailListNext(MailListBean info);

    void getMailListError(String code, String msg);
}

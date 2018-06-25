package com.jinke.housekeeper.saas.report.service.listener;

import com.jinke.housekeeper.bean.RegisterDepartmentBean;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface ContactsActivityListener {
    void getUserListDataonError(String code, String msg);

    void getUserListDataonNext(RegisterDepartmentBean info);
}

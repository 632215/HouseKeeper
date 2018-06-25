package com.jinke.housekeeper.saas.report.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.bean.RegisterDepartmentBean;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface ContactsActivityView{
    void getUserListDataOnError(String code, String msg);

    void getUserListDataOnNext(RegisterDepartmentBean info);
}

package com.jinke.housekeeper.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.bean.RegisterDepartmentBean;

/**
 * Created by Administrator on 2017/9/15.
 */

public interface RegisterDepartmentActivityView {
    void getCJContentDataOnError(String code, String msg);

    void getCJContentDataonNext(RegisterDepartmentBean info);
}

package com.jinke.housekeeper.service.listener;

import com.jinke.housekeeper.bean.RegisterDepartmentBean;

/**
 * Created by Administrator on 2017/9/15.
 */

public interface RegisterDepartmentActivityListener {
    void getCJContentDataOnError(String code, String msg);

    void getCJContentDataOnNext(RegisterDepartmentBean info);
}

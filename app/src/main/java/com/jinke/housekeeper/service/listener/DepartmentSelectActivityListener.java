package com.jinke.housekeeper.service.listener;

import com.jinke.housekeeper.bean.RegisterDepartmentBean;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface DepartmentSelectActivityListener {
    void getCJContentDataonNext(RegisterDepartmentBean info);

    void getCJContentDataonError(String code, String msg);
}

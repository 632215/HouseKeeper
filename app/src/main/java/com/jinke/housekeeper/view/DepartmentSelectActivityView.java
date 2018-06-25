package com.jinke.housekeeper.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.bean.RegisterDepartmentBean;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface DepartmentSelectActivityView extends BaseView{
    void getCJContentDataonNext(RegisterDepartmentBean info);

    void getCJContentDataonError(String code, String msg);
}

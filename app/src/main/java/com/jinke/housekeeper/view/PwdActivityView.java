package com.jinke.housekeeper.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.bean.AppHandleInfo;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface PwdActivityView extends BaseView{
    void getUpdatePwdError(String code, String msg);

    void getUpdatePwdNext();

    void getLoinOutonNext(AppHandleInfo info);

    void getLoinOutonError(String code, String msg);
}

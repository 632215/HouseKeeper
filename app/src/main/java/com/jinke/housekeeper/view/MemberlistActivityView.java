package com.jinke.housekeeper.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.bean.MemberListBean;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface MemberlistActivityView {
    void getMenberListNext(MemberListBean dataBean);

    void getMenberListError(String code, String msg);
}

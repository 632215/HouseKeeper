package com.jinke.housekeeper.service.listener;

import com.jinke.housekeeper.bean.MemberListBean;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface MemberlistActivityListener {
    void getMenberListNext(MemberListBean dataBean);

    void getMenberListError(String code, String msg);
}

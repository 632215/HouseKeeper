package com.jinke.housekeeper.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.bean.MailListBean;

/**
 * Created by Administrator on 2017/9/25.
 */

public interface ContactActivityView extends BaseView{

    void getMailListNext(MailListBean info);

    void getMailListError(String code, String msg);

    void showLoading();
}

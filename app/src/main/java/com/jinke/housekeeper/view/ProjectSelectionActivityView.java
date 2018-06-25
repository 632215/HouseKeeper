package com.jinke.housekeeper.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.bean.CountXMListInfo;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface ProjectSelectionActivityView extends BaseView{
    void getCountXMListonNext(CountXMListInfo info);

    void getCountXMListonErrort(String code, String msg);
}

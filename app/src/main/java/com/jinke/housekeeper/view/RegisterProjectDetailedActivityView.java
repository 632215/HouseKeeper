package com.jinke.housekeeper.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.bean.RegisterProjectBean;

/**
 * Created by Administrator on 2017/9/15.
 */

public interface RegisterProjectDetailedActivityView extends BaseView{
    void getXMListonError(String code, String msg);

    void getXMListonNext(RegisterProjectBean info);
}

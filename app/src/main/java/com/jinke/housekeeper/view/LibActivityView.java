package com.jinke.housekeeper.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.bean.KnowledgeInfo;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface LibActivityView extends BaseView{
    void getLoreonError(String code, String msg);

    void getLoreonNext(KnowledgeInfo info);
}

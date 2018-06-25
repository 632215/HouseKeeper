package com.jinke.housekeeper.saas.patrol.config;

import android.content.Context;

import com.jinke.housekeeper.saas.patrol.bean.SavePointBean;
import com.jinke.housekeeper.saas.patrol.bean.SavePointListBean;
import com.jinke.housekeeper.saas.patrol.utils.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * function: 保存打卡点
 * author: hank
 * date: 2017/8/21
 */

public class SavePointListConfig {

    private final static String fileKey = "SavePointListConfig";
    private final static String key = "SavePointList";

    public static SavePointListBean getSavePointListBean(Context context) {
        SavePointListBean savePointListBean = (SavePointListBean) SharedPreferenceUtil.get(context, fileKey, key);
        //数据为空时初始化存储表
        if (null == savePointListBean) {
            List<SavePointBean> savePointBeanList = new ArrayList<SavePointBean>();
            SavePointBean savePointBean = new SavePointBean();
            savePointBean.setRow(0);
            List<SavePointBean.ListDataBean> listDataBeans = new ArrayList<SavePointBean.ListDataBean>();
            savePointBean.setListData(listDataBeans);
            savePointBeanList.add(savePointBean);
            savePointListBean = new SavePointListBean();
            savePointListBean.setPointListBeen(savePointBeanList);
        }
        return savePointListBean;
    }

    /**
     * 清空直接设置null
     * @param context
     * @param savePointListBean
     */
    public static void setSavePointListBean(Context context, SavePointListBean savePointListBean) {
        SharedPreferenceUtil.save(context, fileKey, key, savePointListBean);
    }
}

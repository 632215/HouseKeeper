package com.jinke.housekeeper.saas.equipment.config;

import android.content.Context;

import com.jinke.housekeeper.saas.equipment.bean.AddPointBean;
import com.jinke.housekeeper.saas.equipment.bean.AddPointListBean;
import com.jinke.housekeeper.saas.equipment.utils.SharedPreferenceUtil;

import java.util.List;

/**
 * function: 保存添加好的设备点
 * author: hank
 * date: 2017/8/21
 */

public class SaveAddPointListConfig {

    private final static String fileKey = "SaveAddPointListConfig";
    private final static String key = "SaveAddPointList";

    public static AddPointListBean getSavePointListBean(Context context) {
        return (AddPointListBean) SharedPreferenceUtil.get(context, fileKey , key);
    }

    public static void setSavePointListBean(Context context , AddPointListBean addPointListBean) {
        SharedPreferenceUtil.save(context , fileKey , key ,addPointListBean);
    }


}
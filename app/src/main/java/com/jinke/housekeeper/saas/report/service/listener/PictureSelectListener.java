package com.jinke.housekeeper.saas.report.service.listener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/14.
 */

public interface PictureSelectListener {
    void setPicRefurbishData(ArrayList<String> pictureList);

    void setPhotoData(String absolutePath);
}

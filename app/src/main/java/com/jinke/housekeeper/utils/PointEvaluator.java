package com.jinke.housekeeper.utils;

import android.animation.TypeEvaluator;

import com.jinke.housekeeper.bean.PointInfo;


/**
 * Created by root on 17-3-14.
 */

public class PointEvaluator implements TypeEvaluator {

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        float startPoint = (float) startValue;
        PointInfo endPoint = (PointInfo) endValue;
        float left = startPoint + fraction * (endPoint.getLeft() - startPoint);
        float right = startPoint + fraction * (endPoint.getRight() - startPoint);
        PointInfo pointInfo = new PointInfo(left, right);
        return pointInfo;
    }

}

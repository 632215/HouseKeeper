package com.jinke.housekeeper.saas.equipment.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by root on 24/11/16.
 */

public class ImgGridView extends GridView {
    public boolean isMeasure;

    public ImgGridView(Context context) {
        super(context);
    }

    public ImgGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImgGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expendSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expendSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        isMeasure = false;
        super.onLayout(changed, l, t, r, b);
    }
}
package com.jinke.housekeeper.saas.handoverroom.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class FillListView extends ListView {

    public FillListView(Context context) {
        super(context);
    }

    public FillListView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public FillListView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expendSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expendSpec);
    }

}
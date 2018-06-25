package com.jinke.housekeeper.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lenovo on 2017/9/22.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {

    private View convertView;
    private SparseArray<View> views;//view缓存

    public BaseViewHolder(View itemView) {
        super(itemView);
        convertView = itemView;
        views = new SparseArray<>();
    }

    //从SparseArray<View> views中查找，有则返回view，无则先缓存再返回view;
    private View getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return view;
    }

    //TextView setText
    public void setText(int viewId, String content) {
        ((TextView) getView(viewId)).setText(content);
    }

    //示例：ImageView 设置背景图
    public void setImageResource(int viewId, int drawableId) {
        View view = getView(viewId);
        if (view instanceof ImageView) {
            ((ImageView) view).setImageResource(drawableId);
        } else
            return;
    }

    public void setOnClickLisener(int viewId, final View.OnClickListener clickListener) {
        View view = getView(viewId);
        view.setOnClickListener(clickListener);
    }

    public void setOnLongClickLisener(int viewId, View.OnLongClickListener longClickListener) {
        View view = getView(viewId);
        view.setOnLongClickListener(longClickListener);
    }

    //....其他各种set、get方法.....
}

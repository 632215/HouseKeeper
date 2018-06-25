package com.jinke.housekeeper.saas.report.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.bean.RegisterDepartmentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 17-3-23.
 */

public class SelecterPuItemAdatper extends BaseAdapter {
    LayoutInflater inflater;
    Context mContext;
    private List<RegisterDepartmentBean.ListObjBean> list = new ArrayList<>();

    public SelecterPuItemAdatper(Context mContext, List<RegisterDepartmentBean.ListObjBean> list) {
        this.mContext = mContext;
        this.list = list;
        inflater = LayoutInflater.from(mContext);

    }

    public void setData(List<RegisterDepartmentBean.ListObjBean> list) {
        this.list = list;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_selecterpugrid, null);
            holder = new ViewHolder();
            holder.keyPoint = (TextView) convertView.findViewById(R.id.tx_pointkey);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        RegisterDepartmentBean.ListObjBean bean = list.get(position);
        holder.keyPoint.setText(bean.getName());
        holder.keyPoint.setBackground(bean.isSelecter() ? mContext.getResources().getDrawable(R.drawable.shape_mywindow_blue_bg)
                : mContext.getResources().getDrawable(R.drawable.shape_item_pink_bg));
        holder.keyPoint.setTextColor(bean.isSelecter() ? mContext.getResources().getColor(R.color.innerblue)
                : mContext.getResources().getColor(R.color.black));
        return convertView;
    }

    ViewHolder holder;

    class ViewHolder {
        TextView keyPoint;

    }
}

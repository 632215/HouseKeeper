package com.jinke.housekeeper.saas.equipment.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.equipment.bean.ReadWatchBean;

import java.util.List;

/**
 * function:
 * author: hank
 * date: 2017/9/15
 */

public class ThreeTableEnterAdapter extends BaseAdapter {

    private List<ReadWatchBean.ListDataBean> infoList;
    private Context context;
    private LayoutInflater inflater;

    public ThreeTableEnterAdapter(Context mContext, List<ReadWatchBean.ListDataBean> infoList) {
        this.context = mContext;
        this.infoList = infoList;
        inflater = LayoutInflater.from(mContext);
    }

    public void setInfoListBean(List<ReadWatchBean.ListDataBean> infoList) {
        this.infoList = infoList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return infoList.size();
    }

    @Override
    public Object getItem(int i) {
        return infoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_three_table_enter, null);
            holder = new viewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.three_table_enter_name);
            holder.location = (TextView) convertView.findViewById(R.id.three_table_enter_location);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        holder.name.setText(infoList.get(position).getName());
        holder.location.setText(infoList.get(position).getPosition());
        return convertView;
    }

    viewHolder holder;

    public class viewHolder {
        TextView name;
        TextView location;
    }
}
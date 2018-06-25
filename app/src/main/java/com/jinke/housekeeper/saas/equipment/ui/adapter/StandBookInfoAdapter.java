package com.jinke.housekeeper.saas.equipment.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jinke.housekeeper.R;

import java.util.List;

/**
 * function: 设备维护Adapter
 * author: hank
 * date: 2017/9/13
 */

public class StandBookInfoAdapter extends BaseAdapter {

    private List<Integer> infoList;
    private Context context;
    private LayoutInflater inflater;

    public StandBookInfoAdapter(Context mContext, List<Integer> infoList) {
        this.context = mContext;
        this.infoList = infoList;
        inflater = LayoutInflater.from(mContext);
    }

    public void setInfoListBean(List<Integer> infoList) {
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
            convertView = inflater.inflate(R.layout.adapter_stand_book_info, null);
            holder = new viewHolder();
            holder.index = (TextView) convertView.findViewById(R.id.adapter_stand_book_info_index);
            holder.info = (TextView) convertView.findViewById(R.id.adapter_stand_book_info);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        holder.index.setText("1");
        holder.info.setText("样板");
        return convertView;
    }

    viewHolder holder;

    public class viewHolder {
        TextView index;
        TextView info;
    }
}

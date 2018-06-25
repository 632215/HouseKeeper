package com.jinke.housekeeper.saas.equipment.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinke.housekeeper.R;

import java.util.List;

/**
 * function:
 * author: hank
 * date: 2017/9/15
 */

public class ThreeTableDetailsAdapter extends BaseAdapter {

    private List<String> infoList;
    private Context context;
    private LayoutInflater inflater;

    public ThreeTableDetailsAdapter(Context mContext, List<String> infoList) {
        this.context = mContext;
        this.infoList = infoList;
        inflater = LayoutInflater.from(mContext);
    }

    public void setInfoListBean(List<String> infoList) {
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
            convertView = inflater.inflate(R.layout.adapter_three_table_details, null);
            holder = new viewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.three_table_details_name);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        holder.name.setText(infoList.get(position));
        return convertView;
    }

    viewHolder holder;

    public class viewHolder {
        TextView name;
    }
}

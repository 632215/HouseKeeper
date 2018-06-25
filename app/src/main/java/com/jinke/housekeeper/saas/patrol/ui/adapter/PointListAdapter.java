package com.jinke.housekeeper.saas.patrol.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.patrol.bean.PointListBean;

import java.util.List;

/**
 * function:
 * author: hank
 * date: 2017/11/13
 */

public class PointListAdapter extends BaseAdapter {
    private List<PointListBean> infoList;
    private Context context;
    private LayoutInflater inflater;

    public PointListAdapter(Context mContext, List<PointListBean> infoList) {
        this.context = mContext;
        this.infoList = infoList;
        inflater = LayoutInflater.from(mContext);
    }

    public void setPointList(List<PointListBean> infoList) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_point_list, null);
            holder = new viewHolder();
            holder.pointName = (TextView) convertView.findViewById(R.id.point_list_name);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        holder.pointName.setText(infoList.get(position).getPointName());
        return convertView;
    }

    viewHolder holder;


    public class viewHolder {
        TextView pointName;
    }

}


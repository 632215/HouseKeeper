package com.jinke.housekeeper.saas.patrol.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.patrol.bean.PointListBean;

import java.util.ArrayList;
import java.util.List;

public class PatrolManageAdapter extends BaseAdapter {
    List<PointListBean> list = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;

    public PatrolManageAdapter(Context mContext, List<PointListBean> list) {
        this.context = mContext;
        this.list = list;
        inflater = LayoutInflater.from(mContext);
    }

    public void setRefreshData(List<PointListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_patrol_manage, null);
            holder = new viewHolder();
            holder.manageLocationLine = (TextView) convertView.findViewById(R.id.patrol_manage_location_line);
            holder.manageLocation = (TextView) convertView.findViewById(R.id.patrol_manage_location);
            holder.manageDayTimes = (TextView) convertView.findViewById(R.id.patrol_manage_day_times);
            holder.manageMonthTimes = (TextView) convertView.findViewById(R.id.patrol_manage_month_times);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        if (list.size() - 1 == position) {
            holder.manageLocationLine.setVisibility(View.VISIBLE);
        } else {
            holder.manageLocationLine.setVisibility(View.INVISIBLE);
        }
        holder.manageLocation.setText(list.get(position).getPointName());
        holder.manageDayTimes.setText(String.valueOf(list.get(position).getTodayNum()) + context.getString(R.string.patrol_manage_times));
        holder.manageMonthTimes.setText(String.valueOf(list.get(position).getMonthNum()) + context.getString(R.string.patrol_manage_times));
        return convertView;
    }

    viewHolder holder;

    public class viewHolder {
        TextView manageLocationLine;
        TextView manageLocation;
        TextView manageDayTimes;
        TextView manageMonthTimes;
    }

}

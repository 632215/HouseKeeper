package com.jinke.housekeeper.saas.patrol.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.patrol.bean.WeekDayBean;

import java.util.List;

public class PatrolWeekAdapter extends BaseAdapter {
    private List<Integer> infoList;
    private Context context;
    private LayoutInflater inflater;


    private WeekDayBean weekDayBean;

    public PatrolWeekAdapter(Context mContext, List<Integer> infoList,WeekDayBean weekDayBean) {
        this.context = mContext;
        this.infoList = infoList;
        inflater = LayoutInflater.from(mContext);
        this.weekDayBean = weekDayBean;
    }

    public void setBluetoothBean(List<Integer> infoList) {
        this.infoList = infoList;
        notifyDataSetChanged();
    }

    public void setWeekDayBean(WeekDayBean weekDayBean) {
        this.weekDayBean = weekDayBean;
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
            convertView = inflater.inflate(R.layout.adapter_patrol_week, null);
            holder = new viewHolder();
            holder.remindWeak = (TextView) convertView.findViewById(R.id.week_day);
            holder.remindWeakChoie = (TextView) convertView.findViewById(R.id.week_day_choice);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }

        switch (infoList.get(position)) {
            case 0:
                if (weekDayBean.isMonday()) {
                    holder.remindWeakChoie.setVisibility(View.VISIBLE);
                } else {
                    holder.remindWeakChoie.setVisibility(View.GONE);
                }
                holder.remindWeak.setText(context.getString(R.string.patrol_remind_add_repeti_monday));
                break;
            case 1:
                if (weekDayBean.isTuesday()) {
                    holder.remindWeakChoie.setVisibility(View.VISIBLE);
                } else {
                    holder.remindWeakChoie.setVisibility(View.GONE);
                }
                holder.remindWeak.setText(context.getString(R.string.patrol_remind_add_repeti_tuesday));
                break;
            case 2:
                if (weekDayBean.isWednesday()) {
                    holder.remindWeakChoie.setVisibility(View.VISIBLE);
                } else {
                    holder.remindWeakChoie.setVisibility(View.GONE);
                }
                holder.remindWeak.setText(context.getString(R.string.patrol_remind_add_repeti_wednesday));
                break;
            case 3:
                if (weekDayBean.isThursday()) {
                    holder.remindWeakChoie.setVisibility(View.VISIBLE);
                } else {
                    holder.remindWeakChoie.setVisibility(View.GONE);
                }
                holder.remindWeak.setText(context.getString(R.string.patrol_remind_add_repeti_thursday));
                break;
            case 4:
                if (weekDayBean.isFriday()) {
                    holder.remindWeakChoie.setVisibility(View.VISIBLE);
                } else {
                    holder.remindWeakChoie.setVisibility(View.GONE);
                }
                holder.remindWeak.setText(context.getString(R.string.patrol_remind_add_repeti_friday));
                break;
            case 5:
                if (weekDayBean.isSaturday()) {
                    holder.remindWeakChoie.setVisibility(View.VISIBLE);
                } else {
                    holder.remindWeakChoie.setVisibility(View.GONE);
                }
                holder.remindWeak.setText(context.getString(R.string.patrol_remind_add_repeti_saturday));
                break;
            case 6:
                if (weekDayBean.isSunday()) {
                    holder.remindWeakChoie.setVisibility(View.VISIBLE);
                } else {
                    holder.remindWeakChoie.setVisibility(View.GONE);
                }
                holder.remindWeak.setText(context.getString(R.string.patrol_remind_add_repeti_sunday));
                break;
        }
        return convertView;
    }

    viewHolder holder;

    public class viewHolder {
        TextView remindWeak;
        TextView remindWeakChoie;
    }

}

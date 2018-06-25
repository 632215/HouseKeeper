package com.jinke.housekeeper.saas.patrol.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.patrol.bean.PointPlanBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author : huominghao
 * date : 2018/1/26 0026
 * function :
 */

public class PointPlanListAdapter extends BaseAdapter {

    private List<PointPlanBean.ListDataBean> infoList;
    private Context context;
    private LayoutInflater inflater;


    public PointPlanListAdapter(Context mContext, List<PointPlanBean.ListDataBean> infoList) {
        this.context = mContext;
        this.infoList = infoList;
        inflater = LayoutInflater.from(context);
    }


    public void setPointPlanBean(List<PointPlanBean.ListDataBean> infoList) {
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
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.adapter_point_plan_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == infoList.size()-1){
            holder.pointPlanLine.setVisibility(View.INVISIBLE);
        }
        holder.pointPlanName.setText(infoList.get(position).getTaskName());
        holder.pointPlanTime.setText(infoList.get(position).getStartTime() + "-" + infoList.get(position).getEndTime());
        return convertView;
    }

    ViewHolder holder;

    static class ViewHolder {
        @Bind(R.id.point_plan_name)
        TextView pointPlanName;
        @Bind(R.id.point_plan_time)
        TextView pointPlanTime;
        @Bind(R.id.point_plan_line)
        TextView pointPlanLine;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

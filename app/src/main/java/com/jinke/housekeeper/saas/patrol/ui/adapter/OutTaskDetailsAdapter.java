package com.jinke.housekeeper.saas.patrol.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.patrol.bean.TimeOutTaskListBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author : huominghao
 * date : 2018/2/5 0005
 * function :
 */

public class OutTaskDetailsAdapter extends BaseAdapter {
    private List<TimeOutTaskListBean.PointListBean> infoList;
    private String taskName;
    private Context context;
    private LayoutInflater inflater;

    public OutTaskDetailsAdapter(Context mContext, List<TimeOutTaskListBean.PointListBean> infoList,String taskName) {
        this.context = mContext;
        this.infoList = infoList;
        this.taskName =taskName;
        inflater = LayoutInflater.from(context);
    }

    public void setPointListBeanList(List<TimeOutTaskListBean.PointListBean> infoList) {
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
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.adapter_out_task_details, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.outTimePlaneName.setText(taskName);
        holder.outTimePointName.setText(infoList.get(position).getName());
        return convertView;
    }

    ViewHolder holder;


    static class ViewHolder {
        @Bind(R.id.out_time_plane_name)
        TextView outTimePlaneName;
        @Bind(R.id.out_time_point_name)
        TextView outTimePointName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

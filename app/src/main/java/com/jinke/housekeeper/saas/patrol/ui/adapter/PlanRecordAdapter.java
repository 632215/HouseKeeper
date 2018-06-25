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

public class PlanRecordAdapter extends BaseAdapter {
    private List<TimeOutTaskListBean> infoList;
    private Context context;
    private LayoutInflater inflater;

    public PlanRecordAdapter(Context mContext, List<TimeOutTaskListBean> infoList) {
        this.context = mContext;
        this.infoList = infoList;
        inflater = LayoutInflater.from(context);
    }

    public void setTimeOutTaskListBeanList(List<TimeOutTaskListBean> infoList) {
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
            convertView = inflater.inflate(R.layout.adapter_plan_record, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String state = "未开始";
        switch (infoList.get(position).getState()) {
            case 1://已完成
                state = "已完成";
                holder.planRecordTimeOutSize.setVisibility(View.INVISIBLE);
                holder.planRecordNameIco.setVisibility(View.INVISIBLE);
                break;
            case 2://进行中
                state = "进行中";
                holder.planRecordTimeOutSize.setVisibility(View.INVISIBLE);
                holder.planRecordNameIco.setVisibility(View.INVISIBLE);
                break;
            case 3://任务超时
                state = "漏检";
                holder.planRecordTimeOutSize.setText(infoList.get(position).getPointNum() + "个");
                holder.planRecordTimeOutSize.setVisibility(View.VISIBLE);
                holder.planRecordNameIco.setVisibility(View.VISIBLE);
                break;
            case 4://未开始
                state = "未开始";
                holder.planRecordTimeOutSize.setVisibility(View.INVISIBLE);
                holder.planRecordNameIco.setVisibility(View.INVISIBLE);
                break;
        }
        holder.planRecordName.setText(infoList.get(position).getTaskName() + "  " + infoList.get(position).getStartTime() + "-" + infoList.get(position).getEndTime());
        holder.planRecordState.setText(state);
        return convertView;
    }

    ViewHolder holder;


    static class ViewHolder {
        @Bind(R.id.plan_record_state)
        TextView planRecordState;
        @Bind(R.id.plan_record_time_out_size)
        TextView planRecordTimeOutSize;
        @Bind(R.id.plan_record_name)
        TextView planRecordName;
        @Bind(R.id.plan_record_next_ico)
        TextView planRecordNameIco;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

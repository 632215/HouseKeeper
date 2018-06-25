package com.jinke.housekeeper.saas.equipment.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.equipment.bean.DailyPatrolRecordListBean;
import com.jinke.housekeeper.saas.equipment.bean.TaskBean;
import com.jinke.housekeeper.saas.equipment.ui.activity.BluetoothLinkActivity;
import com.jinke.housekeeper.saas.equipment.ui.widget.CustomListView;
import com.jinke.housekeeper.saas.equipment.utils.LogUtil;
import com.jinke.housekeeper.saas.report.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * function: 当前任务列表
 * author: hank
 * date: 2017/9/11
 */

public class DailyPatrolRecordAdapter extends BaseAdapter {

    private List<DailyPatrolRecordListBean> infoList;
    private Context context;
    private LayoutInflater inflater;

    public DailyPatrolRecordAdapter(Context mContext, List<DailyPatrolRecordListBean> infoList) {
        this.context = mContext;
        this.infoList = infoList;
        inflater = LayoutInflater.from(mContext);
    }

    public void setInfoListBean(List<DailyPatrolRecordListBean> infoList) {
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
            convertView = inflater.inflate(R.layout.adapter_daily_patrol_record, null);
            holder = new viewHolder();
            holder.mLinearLayout = (RelativeLayout) convertView.findViewById(R.id.equipment_polling_layout);
            holder.pollingTime = (TextView) convertView.findViewById(R.id.equipment_polling_time);
            holder.pollingState = (TextView) convertView.findViewById(R.id.equipment_polling_state);
            holder.pollingListView = (CustomListView) convertView.findViewById(R.id.daily_patrol_record_list);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        TaskBean taskBean = infoList.get(position).getTaskBean();
        final String taskTime = taskBean.getStartTime() + context.getString(R.string.daily_patrol_to) + taskBean.getEndTime();
        final String taskId = taskBean.getId();
        final String taskStatus = taskBean.getTaskStatus();
        holder.pollingTime.setText(taskTime);
        //设置对应任务数据
        List<TaskBean.PointListBean> integerList;
        integerList = taskBean.getPointList();
        if (null == integerList) {
            integerList = new ArrayList<>();
        }
        DailyPatrolRecordDetailsAdapter dailyPatrolRecordDetailsAdapter = new DailyPatrolRecordDetailsAdapter(context, integerList, taskTime, taskId, taskStatus);
        holder.pollingListView.setAdapter(dailyPatrolRecordDetailsAdapter);
        final List<TaskBean.PointListBean> finalIntegerList = integerList;
        holder.pollingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (TaskBean.TASK_TO_PERFORM.equals(taskStatus) && "".equals(finalIntegerList.get(position).getStatus())) {
                    Intent checkingInformationIntent = new Intent(context, BluetoothLinkActivity.class);
                    checkingInformationIntent.putExtra("date", "DAILY_PATROL_DETAILS");
                    checkingInformationIntent.putExtra("listBean", finalIntegerList.get(position));
                    checkingInformationIntent.putExtra("taskTime", taskTime);
                    checkingInformationIntent.putExtra("taskId", taskId);
                    context.startActivity(checkingInformationIntent);
                } else if (TaskBean.TASK_NO_TSTARTED.equals(taskStatus)) {
                    ToastUtils.showLong(context, context.getString(R.string.daily_patrol_task_hint_1));
                }

            }
        });

        //TASK_NO_TSTARTED 未开始，TASK_TO_PERFORM 待执行，TASK_COMPLETED 已完成，TASK_TIMEOUT 已超时
        switch (taskStatus) {
            case TaskBean.TASK_NO_TSTARTED:
                holder.pollingState.setText(R.string.daily_patrol_pack_tstarted);
                break;
            case TaskBean.TASK_TO_PERFORM:
                holder.pollingState.setText(R.string.daily_patrol_pack_perform);
                break;
            case TaskBean.TASK_COMPLETED:
                holder.pollingState.setText(R.string.daily_patrol_pack_completed);
                break;
            case TaskBean.TASK_TIMEOUT:
                holder.pollingState.setText(R.string.daily_patrol_pack_timeout);
                break;
        }
        if (infoList.get(position).isShowState()) {
            holder.pollingState.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.equipment_open_ico, 0);
            holder.pollingListView.setVisibility(View.VISIBLE);
            dailyPatrolRecordDetailsAdapter.setInfoListBean(integerList, taskTime, taskId, taskStatus);
        } else {
            holder.pollingState.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.equipment_next_ico, 0);
            holder.pollingListView.setVisibility(View.GONE);
            dailyPatrolRecordDetailsAdapter.setInfoListBean(integerList, taskTime, taskId, taskStatus);
        }
        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoList.get(position).setShowState(!infoList.get(position).isShowState());
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    viewHolder holder;

    public class viewHolder {
        RelativeLayout mLinearLayout;
        TextView pollingTime;
        TextView pollingState;
        CustomListView pollingListView;
    }

}

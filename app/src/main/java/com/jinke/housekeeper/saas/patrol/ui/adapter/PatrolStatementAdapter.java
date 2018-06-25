package com.jinke.housekeeper.saas.patrol.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.patrol.bean.PointListBean;
import com.jinke.housekeeper.saas.patrol.bean.PointProjectDataBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author : huominghao
 * date : 2018/3/6 0006
 * function :
 */

public class PatrolStatementAdapter extends BaseAdapter {
    List<PointProjectDataBean> list = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;

    public PatrolStatementAdapter(Context mContext, List<PointProjectDataBean> list) {
        this.context = mContext;
        this.list = list;
        inflater = LayoutInflater.from(mContext);
    }

    public void setRefreshData(List<PointProjectDataBean> list) {
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
            convertView = inflater.inflate(R.layout.adapter_patrol_statement, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (list.size() - 1 == position) {
            viewHolder.patrolManageLocationLine.setVisibility(View.VISIBLE);
        } else {
            viewHolder.patrolManageLocationLine.setVisibility(View.INVISIBLE);
        }
        String name="";
        switch (list.get(position).getType()){
            case 1:
                name = list.get(position).getPointName();
                break;
            case 2:
                name = list.get(position).getPlanName();
                break;
            case 3:
                name = list.get(position).getProjectName();
                break;
        }
        viewHolder.datePickerMapCompany.setText(name);
        viewHolder.datePickerMapLeak.setText(list.get(position).getToDayLou());
        viewHolder.datePickerMapReality.setText(list.get(position).getToDayComplent());
        viewHolder.datePickerMapPlan.setText(list.get(position).getToDayPlan());
        viewHolder.datePickerMapPercentage
                .setText(String.valueOf((int)(Float.parseFloat(list.get(position).getToDayComplent())/Float.parseFloat(list.get(position).getToDayPlan()) * 100)) + "%");
        return convertView;
    }


    private ViewHolder viewHolder;

    static class ViewHolder {
        @Bind(R.id.date_picker_map_company)
        TextView datePickerMapCompany;
        @Bind(R.id.patrol_manage_location_line)
        TextView patrolManageLocationLine;
        @Bind(R.id.date_picker_map_leak)
        TextView datePickerMapLeak;
        @Bind(R.id.date_picker_map_reality)
        TextView datePickerMapReality;
        @Bind(R.id.date_picker_map_plan)
        TextView datePickerMapPlan;
        @Bind(R.id.date_picker_map_percentage)
        TextView datePickerMapPercentage;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

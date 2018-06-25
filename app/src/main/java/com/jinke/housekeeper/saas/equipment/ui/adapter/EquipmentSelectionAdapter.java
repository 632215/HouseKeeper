package com.jinke.housekeeper.saas.equipment.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.equipment.bean.DeviceTypeBean;

import java.util.List;

/**
 * function: 选择设备Adapter
 * author: hank
 * date: 2017/9/13
 */

public class EquipmentSelectionAdapter extends BaseAdapter {

    private List<DeviceTypeBean.ListDataBean> infoList;
    private Context context;
    private LayoutInflater inflater;

    public EquipmentSelectionAdapter(Context mContext, List<DeviceTypeBean.ListDataBean> infoList) {
        this.context = mContext;
        this.infoList = infoList;
        inflater = LayoutInflater.from(mContext);
    }

    public void setInfoListBean(List<DeviceTypeBean.ListDataBean> infoList) {
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
            convertView = inflater.inflate(R.layout.adapter_equipment_selection, null);
            holder = new viewHolder();
            holder.location = (TextView) convertView.findViewById(R.id.equipment_selection_location);
            holder.name = (TextView) convertView.findViewById(R.id.equipment_selection_name);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        holder.location.setText(infoList.get(position).getName());
        if (null != infoList.get(position).getInstallationOcation() && !"".equals(infoList.get(position).getInstallationOcation())) {
            holder.name.setText(infoList.get(position).getInstallationOcation());
            holder.name.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0);
        }
        return convertView;
    }

    viewHolder holder;

    public class viewHolder {
        TextView location;
        TextView name;
    }
}

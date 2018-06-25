package com.jinke.housekeeper.saas.equipment.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.equipment.bean.SetBeanList;

import java.util.List;

/**
 * function: 连接设置
 * author: hank
 * date: 2017/9/11
 */

public class SetAdapter extends BaseAdapter {

    private List<SetBeanList.SetBean> infoList;
    private Context context;
    private LayoutInflater inflater;

    public SetAdapter(Context mContext, List<SetBeanList.SetBean> infoList) {
        this.context = mContext;
        this.infoList = infoList;
        inflater = LayoutInflater.from(mContext);
    }

    public void setInfoListBean(List<SetBeanList.SetBean> infoList) {
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
            convertView = inflater.inflate(R.layout.adapter_set, null);
            holder = new viewHolder();
            holder.setString = (TextView) convertView.findViewById(R.id.adapter_link_set_string);
            holder.setState = (TextView) convertView.findViewById(R.id.adapter_link_set_state);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        holder.setString.setText(infoList.get(position).getSetString());
        if (infoList.get(position).isSetState()) {
            holder.setState.setVisibility(View.VISIBLE);
        } else {
            holder.setState.setVisibility(View.GONE);
        }
        return convertView;
    }

    viewHolder holder;

    public class viewHolder {
        TextView setString;
        TextView setState;
    }
}

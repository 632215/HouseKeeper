package com.jinke.housekeeper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.bean.RegisterDepartmentBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class ContactsAdapter extends BaseAdapter {

    private List<RegisterDepartmentBean.ListObjBean> infoList;
    private Context context;
    private LayoutInflater inflater;

    public ContactsAdapter(Context mContext, List<RegisterDepartmentBean.ListObjBean> infoList) {
        this.context = mContext;
        this.infoList = infoList;
        inflater = LayoutInflater.from(mContext);
    }

    public void setInfoListBean(List<RegisterDepartmentBean.ListObjBean> infoList) {
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
            convertView = inflater.inflate(R.layout.item_registerdepartment, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvItemRegisterdepartment.setText(infoList.get(position).getName());
        return convertView;
    }

    ViewHolder holder;


    static class ViewHolder {
        @Bind(R.id.tv_item_registerdepartment)
        TextView tvItemRegisterdepartment;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

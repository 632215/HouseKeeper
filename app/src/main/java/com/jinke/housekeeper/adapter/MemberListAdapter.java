package com.jinke.housekeeper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.bean.MemberListBean;

import java.util.ArrayList;
import java.util.List;


public class MemberListAdapter extends BaseAdapter {
    private Context mContext;
    private List<MemberListBean.ObjBean> list = new ArrayList<>();
    private LayoutInflater inflater;
    private OnNotifGroupListener listener;

    public MemberListAdapter(Context mContext, List<MemberListBean.ObjBean> list) {
        this.list = list;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    public void setOnNotifGroupListener(OnNotifGroupListener listener) {
        this.listener = listener;
    }


    public void setData(List<MemberListBean.ObjBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_memberlist, null);
            groupHolder = new GroupHolder();
            groupHolder.tv_memberlist_deparment = (TextView) convertView.findViewById(R.id.tv_memberlist_deparment);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        groupHolder.tv_memberlist_deparment.setText(list.get(position).getName()+"("+list.get(position).getUlist().size()+")");
        return convertView;
    }

    GroupHolder groupHolder;

    class GroupHolder {
        TextView tv_memberlist_deparment;
        ImageView more;
    }

    public interface OnNotifGroupListener {
        public void notifGroup();
    }
}

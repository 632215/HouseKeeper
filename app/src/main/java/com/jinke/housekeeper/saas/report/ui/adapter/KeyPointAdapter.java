//package com.jinke.housekeeper.saas.report.ui.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseExpandableListAdapter;
//import android.widget.TextView;
//
//import com.jinke.housekeeper.R;
//import com.jinke.housekeeper.saas.report.bean.KeyPointBean;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by 32 on 2016/12/22.
// */
//public class KeyPointAdapter extends BaseExpandableListAdapter {
//    private List<KeyPointBean.ListObjBean> list = new ArrayList<>();
//    LayoutInflater inflater;
//    private Context mContext;
//    public OnItemOnClickListener listener;
//
//    public void setOnItemOnClickListener(OnItemOnClickListener listener) {
//        this.listener = listener;
//    }
//
//    public interface OnItemOnClickListener {
//        void OnItem(String name, String id);
//    }
//
//    public KeyPointAdapter(List<KeyPointBean.ListObjBean> list, Context mContext) {
//        this.mContext = mContext;
//        this.list = list;
//        inflater = LayoutInflater.from(mContext);
//    }
//
//
//    public void setData(List<KeyPointBean.ListObjBean> list) {
//        this.list = list;
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public int getGroupCount() {
//        return list.size();
//    }
//
//    @Override
//    public long getGroupId(int groupPosition) {
//        return groupPosition;
//    }
//
//    @Override
//    public Object getGroup(int groupPosition) {
//        return list.get(groupPosition);
//    }
//
//    @Override
//    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            convertView = inflater.inflate(R.layout.item_keypoint, null);
//            groupHolder = new GroupHolder();
//            groupHolder.tv_item_registerdepartment = (TextView) convertView.findViewById(R.id.tv_item_registerdepartment);
//            convertView.setTag(groupHolder);
//        } else {
//            groupHolder = (GroupHolder) convertView.getTag();
//        }
//        groupHolder.tv_item_registerdepartment.setText(list.get(groupPosition).getKey());
//        return convertView;
//    }
//
//    @Override
//    public int getChildrenCount(int groupPosition) {
//        return list.get(groupPosition).getValue().size();
//    }
//
//    @Override
//    public long getChildId(int groupPosition, int childPosition) {
//        return list.get(groupPosition).getValue().get(childPosition).getId();
//    }
//
//
//    @Override
//    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            convertView = inflater.inflate(R.layout.item_innerlist, null);
//            childHolder = new ChildHolder();
//            childHolder.tv_registerdepartment = (TextView) convertView.findViewById(R.id.tv_item_registerdepartment);
//            convertView.setTag(childHolder);
//        } else {
//            childHolder = (ChildHolder) convertView.getTag();
//        }
//        final KeyPointBean.ListObjBean.ValueBean bean = list.get(groupPosition).getValue().get(childPosition);
//        childHolder.tv_registerdepartment.setText(bean.getName());
//        childHolder.tv_registerdepartment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.OnItem(bean.getName(), String.valueOf(bean.getId()));
//            }
//        });
//        return convertView;
//    }
//
//    @Override
//    public Object getChild(int groupPosition, int childPosition) {
//        return list.get(groupPosition).getValue().get(childPosition);
//    }
//
//    @Override
//    public boolean hasStableIds() {
//        return false;
//    }
//
//
//    @Override
//    public boolean isChildSelectable(int groupPosition, int childPosition) {
//        return false;
//    }
//
//    GroupHolder groupHolder;
//
//    public class GroupHolder {
//        TextView tv_item_registerdepartment;
//
//    }
//
//    ChildHolder childHolder;
//
//    class ChildHolder {
//        TextView tv_registerdepartment;
//
//    }
//}

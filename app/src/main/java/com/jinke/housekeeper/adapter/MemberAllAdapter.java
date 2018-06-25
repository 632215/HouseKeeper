package com.jinke.housekeeper.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.bean.MemberListBean;
import com.jinke.housekeeper.ui.activity.fragmentuser.MemberDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 6/01/17.
 */

public class MemberAllAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<MemberListBean.UserBean> listObj = new ArrayList<>();

    public MemberAllAdapter(Context mContext, List<MemberListBean.UserBean> listObj) {
        inflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.listObj = listObj;
    }

    public void setData(List<MemberListBean.UserBean> listObj) {
        this.listObj = listObj;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listObj.size();
    }

    @Override
    public Object getItem(int position) {
        return listObj.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private int headNum = 0;
    private int headName[] = {R.drawable.shape_head1, R.drawable.shape_head2
            , R.drawable.shape_head3, R.drawable.shape_head4};

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_member, parent, false);
            holder = new ViewHolder();
            holder.headImag = (TextView) convertView.findViewById(R.id.headImag);
            holder.name = (TextView) convertView.findViewById(R.id.tv_member_name);
            holder.state = (TextView) convertView.findViewById(R.id.tv_member_state);
            holder.position = (TextView) convertView.findViewById(R.id.iv_member_position);
            holder.line = (TextView) convertView.findViewById(R.id.line);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.line.setVisibility(position == listObj.size() - 1 ? View.GONE : View.VISIBLE);

        final MemberListBean.UserBean bean = listObj.get(position);
        if (bean != null) {
            headNum++;
            if (headNum > 3) {
                headNum = 0;
            }

            holder.headImag.setBackgroundResource(headName[headNum]);
            if (bean.getNickName() != null && bean.getNickName().toString().length() > 1) {
                holder.headImag.setText(bean.getNickName().substring(0, 1));
            }
            holder.name.setText(bean.getNickName());

            switch (bean.getCheckStatus()) {
                case "Y":
                    holder.state.setVisibility(View.INVISIBLE);
                    break;
                case "N":
                case "null":
                    holder.state.setVisibility(View.VISIBLE);
                    holder.state.setText("未审核");
                    break;
            }
            holder.position.setText(bean.getFax());
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MemberDetailActivity.class);
                MemberListBean.ObjBean.UlistBean ulistBean = new MemberListBean.ObjBean.UlistBean();
                ulistBean.setId(bean.getId());
                ulistBean.setCheckStatus(bean.getCheckStatus());
                ulistBean.setFax(bean.getFax());
                ulistBean.setNickName(bean.getNickName());
                ulistBean.setOrg(bean.getOrg());
                ulistBean.setPhone(bean.getPhone());
                ulistBean.setScenes(bean.getScenes());
                intent.putExtra("ulistBean", ulistBean);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    public ViewHolder holder;

    class ViewHolder {
        TextView headImag;
        TextView name;
        TextView state;
        TextView position;
        TextView line;
    }
}

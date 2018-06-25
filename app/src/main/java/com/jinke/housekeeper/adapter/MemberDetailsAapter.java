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
 * Created by root on 17-4-6.
 */

public class MemberDetailsAapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context mContex;
    private List<MemberListBean.ObjBean.UlistBean> list = new ArrayList<>();

    public MemberDetailsAapter(Context mContex, List<MemberListBean.ObjBean.UlistBean> list) {
        this.mContex = mContex;
        this.list = list;
        inflater = LayoutInflater.from(mContex);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
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
            convertView = inflater.inflate(R.layout.item_member, null);
            holdView = new HoldView();
            holdView.headImag = (TextView) convertView.findViewById(R.id.headImag);
            holdView.iv_member_position = (TextView) convertView.findViewById(R.id.iv_member_position);
            holdView.tv_member_state = (TextView) convertView.findViewById(R.id.tv_member_state);
            holdView.tv_member_name = (TextView) convertView.findViewById(R.id.tv_member_name);
            convertView.setTag(holdView);
        } else {
            holdView = (HoldView) convertView.getTag();
        }
        final MemberListBean.ObjBean.UlistBean bean = list.get(position);
        if (bean == null) {
            return convertView;
        }
        headNum++;
        if (headNum > 3) {
            headNum = 0;
        }

        holdView.headImag.setBackgroundResource(headName[headNum]);
        holdView.headImag.setText(bean.getNickName());

//        holdView.headImag.setText(bean.getNickName().substring(bean.getNickName().toString().trim().length()-2,bean.getNickName().toString().trim().length() ));
        holdView.tv_member_name.setText(bean.getNickName());
        holdView.iv_member_position.setText(bean.getFax());
        switch (bean.getCheckStatus()) {
            case "Y":
                holdView.tv_member_state.setVisibility(View.INVISIBLE);
                break;
            case "N":
            case "null":
                holdView.tv_member_state.setVisibility(View.VISIBLE);
                holdView.tv_member_state.setText("未审核");
                break;
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContex, MemberDetailActivity.class);
                MemberListBean.ObjBean.UlistBean ulistBean = new MemberListBean.ObjBean.UlistBean();
                ulistBean.setId(bean.getId());
                ulistBean.setCheckStatus(bean.getCheckStatus());
                ulistBean.setFax(bean.getFax());
                ulistBean.setNickName(bean.getNickName());
                ulistBean.setOrg(bean.getOrg());
                ulistBean.setPhone(bean.getPhone());
                ulistBean.setScenes(bean.getScenes());
                intent.putExtra("ulistBean", ulistBean);
                mContex.startActivity(intent);
            }
        });

        return convertView;
    }

    HoldView holdView;

    class HoldView {
        TextView iv_member_position;
        TextView tv_member_state;
        TextView tv_member_name;
        TextView headImag;

    }
}

package com.jinke.housekeeper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.bean.RegisterDepartmentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 32 on 2016/12/22.
 */
public class RegisterDepartmentAdapter  extends RecyclerView.Adapter<RegisterDepartmentAdapter.ViewHolder>{
    private List<RegisterDepartmentBean.ListObjBean> mArrayList=new ArrayList<>();
    private Context mContext;
    LayoutInflater inflater;
    private RegisterDepartmentAdapter.OnItemClickLitener mOnItemClickLitener;//监听item

    public interface OnItemClickLitener
    {
        void onItemClick(RegisterDepartmentBean.ListObjBean bean);
    }
    public void setOnItemClickLitener(RegisterDepartmentAdapter.OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public RegisterDepartmentAdapter(List<RegisterDepartmentBean.ListObjBean> mArrayList, Context mContext) {
        this.mArrayList = mArrayList;
        inflater=LayoutInflater.from(mContext);
    }

    public void setData(List<RegisterDepartmentBean.ListObjBean> mArrayList){
        this.mArrayList=mArrayList;
        notifyDataSetChanged();
    }
    @Override
    public RegisterDepartmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.item_registerdepartment,parent,false);
        RegisterDepartmentAdapter.ViewHolder viewHolder=new RegisterDepartmentAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RegisterDepartmentAdapter.ViewHolder holder, int position) {
        final RegisterDepartmentAdapter.ViewHolder itemHolder =holder;
        if (mOnItemClickLitener != null) {
            itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = itemHolder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(mArrayList.get(pos));
                }
            });
        }
        itemHolder.bindHolder(mArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_registerDepartment;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_registerDepartment= (TextView) itemView.findViewById(R.id.tv_item_registerdepartment);
        }

        public void bindHolder(RegisterDepartmentBean.ListObjBean _registerDepartmentBean){
            tv_registerDepartment.setText(_registerDepartmentBean.getName());
        }
    }
}

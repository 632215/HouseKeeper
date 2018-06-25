package com.jinke.housekeeper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.bean.RegisterProjectBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 32 on 2016/12/22.
 */
public class RegisterProjectAdapter extends
        RecyclerView.Adapter<RegisterProjectAdapter.ViewHolder> {
    private List<RegisterProjectBean.ListObjBean> mArrayList;
    private Context mContext;
    LayoutInflater inflater;

    private OnItemClickLitener mOnItemClickLitener;//监听item

    public interface OnItemClickLitener {
        void onItemClick(RegisterProjectBean.ListObjBean bean, int postion);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public RegisterProjectAdapter(List<RegisterProjectBean.ListObjBean> mArrayList, Context mContext) {
        this.mArrayList = mArrayList;
        inflater = LayoutInflater.from(mContext);
    }

    public void setData(List<RegisterProjectBean.ListObjBean> mArrayList) {
        this.mArrayList = mArrayList;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_registerproject, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(mArrayList.get(position), position);
                }
            });
        }
        holder.bindHolder(mArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_registerProject;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_registerProject = (TextView) itemView.findViewById(R.id.tv_project_registerproject);
        }

        public void bindHolder(RegisterProjectBean.ListObjBean registerProjectBean) {
            tv_registerProject.setText(registerProjectBean.getName());
        }
    }
}

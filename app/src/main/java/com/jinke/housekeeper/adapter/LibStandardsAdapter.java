package com.jinke.housekeeper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.bean.KnowledgeInfo;
import com.jinke.housekeeper.ui.widget.RecyclerImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 32 on 2016/12/22.
 */

public class LibStandardsAdapter extends RecyclerView.Adapter<LibStandardsAdapter.ViewHolder> {

    private Context mContext;
    LayoutInflater inflater;
    private List<KnowledgeInfo.ListObjBean> mArrayList = new ArrayList<>();
    private OnItemClickLitener mOnItemClickLitener;//监听item

    public interface OnItemClickLitener {
        void onItemClick(String id);
    }

    public void setData(List<KnowledgeInfo.ListObjBean> mArrayList) {
        this.mArrayList = mArrayList;
        notifyDataSetChanged();

    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public LibStandardsAdapter(List<KnowledgeInfo.ListObjBean> mArrayList, Context mContext) {
        this.mArrayList = mArrayList;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_lib, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        System.out.println("position" + position);
        ViewHolder itemHolder = holder;
        if (mOnItemClickLitener != null) {
            itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(mArrayList.get(position).getId() + "");
                }
            });
        }
        itemHolder.bindHolder(mArrayList.get(position).getImgurl());
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (RecyclerImageView) itemView.findViewById(R.id.iv_lib_standards);
        }

        public void bindHolder(String path) {
            Picasso.with(mContext).load(path)
                    .memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)//加速内存的回收
                    .placeholder(R.drawable.moren)//加载中
                    .error(R.drawable.moren)//加载失败
                    .into(imageView);
        }
    }
}

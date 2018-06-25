package com.jinke.housekeeper.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jinke.housekeeper.applicaption.MyApplicaption;

import java.util.List;

/**
 * Created by lenovo on 217/9/22.
 */

public abstract class BaseQuickAdapter<K> extends RecyclerView.Adapter<BaseViewHolder> {

    private List<K> data;
    private int layoutId;
    private Context mContext;



    //暴露给用户的接口,绑定数据
    public abstract void convert(BaseViewHolder baseViewHolder, K itemData);

    public BaseQuickAdapter(Context mContext,List<K> data, int layoutId) {
        this.data = data;
        this.layoutId = layoutId;
        this.mContext=mContext;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(MyApplicaption.getInstance()).inflate(layoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (data == null || data.size() == 0)
            return;
        convert(holder, data.get(position));
    }

    public void setData(List<K> data){
        this.data=data;
        notifyDataSetChanged();
    }


    public interface  OnItemListener{

    }
//    public interface
    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


}

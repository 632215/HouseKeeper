package com.jinke.housekeeper.saas.report.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.jinke.housekeeper.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by root on 17-3-23.
 */
public class GridAdatper<T> extends BaseAdapter {
    LayoutInflater inflater;
    Context mContext;
    private List<T> dataList;
    private PicClickListener listener;
    private String picAddress;

    public GridAdatper(Context mContext, List<T> list, PicClickListener listener) {
        this.mContext = mContext;
        this.listener = listener;
        this.dataList = list;
        inflater = LayoutInflater.from(mContext);
    }

    public void setData(List<T> list) {
        this.dataList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_picture, null);
            holder = new ViewHolder();
            holder.imgPicTure = (ImageView) convertView.findViewById(R.id.imgPicture);
            holder.imgPictureDelete = (ImageView) convertView.findViewById(R.id.imgPictureDelete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        picAddress = (String) dataList.get(position);
//        Picasso.with(mContext).load(new File((picAddress)))
        if (picAddress != "") {
            Picasso.with(mContext).load(picAddress)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//加速内存的回收
                    .placeholder(R.drawable.jk_icon)//加载中
                    .error(R.drawable.jk_icon)//加载失败
                    .into(holder.imgPicTure);
        } else {
            holder.imgPicTure.setImageResource(R.drawable.jk_icon);
        }
        holder.imgPictureDelete.setVisibility(View.GONE);
        holder.imgPicTure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.enlargeImage(position);
            }
        });
        return convertView;
    }

    ViewHolder holder;

    class ViewHolder {
        ImageView imgPicTure;
        ImageView imgPictureDelete;
    }

    public interface PicClickListener<T> {
        void enlargeImage(int picAddress);
    }
}
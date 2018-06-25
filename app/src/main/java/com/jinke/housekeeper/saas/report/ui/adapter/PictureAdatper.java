package com.jinke.housekeeper.saas.report.ui.adapter;

import android.content.Context;
import android.util.Log;
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
public class PictureAdatper<T> extends BaseAdapter {
    LayoutInflater inflater;
    Context mContext;
    private List<T> dataList;
    private PicDeleteListener listener;
    private String picAddress;

    public PictureAdatper(Context mContext, List<T> dataList, PicDeleteListener listener) {
        this.mContext = mContext;
        this.dataList = dataList;
        this.listener = listener;
        inflater = LayoutInflater.from(mContext);
    }

    public void setData(List<T> dataList) {
        this.dataList = dataList;
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

        Picasso.with(mContext).load(new File((picAddress)))
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//加速内存的回收
                .placeholder(R.drawable.jk_icon)//加载中
                .error(R.drawable.jk_icon)//加载失败
                .into(holder.imgPicTure);
        if (position == dataList.size() - 1) {
            Picasso.with(mContext).load(R.drawable.add_picture2x)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//加速内存的回收
                    .placeholder(R.drawable.jk_icon)//加载中
                    .error(R.drawable.jk_icon)//加载失败
                    .into(holder.imgPicTure);
            holder.imgPictureDelete.setVisibility(View.GONE);
            holder.imgPicTure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.addPictureCallBack();
                }
            });
        } else {
            holder.imgPictureDelete.setVisibility(View.VISIBLE);
            holder.imgPictureDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.picDeleteCallBack(dataList.get(position), position);
                }
            });
            holder.imgPicTure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    listener.addPictureCallBack();
                    Log.e("32s", "listener.enlargeImage");
                    listener.enlargeImage(position);
                }
            });
        }

        return convertView;
    }

    ViewHolder holder;

    class ViewHolder {
        ImageView imgPicTure;
        ImageView imgPictureDelete;
    }

    public interface PicDeleteListener<T> {
        void picDeleteCallBack(T t, int position);

        void addPictureCallBack();

        void enlargeImage(int picAddress);
    }
}
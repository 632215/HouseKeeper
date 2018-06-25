package com.jinke.housekeeper.saas.report.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.report.ui.widget.MyGridView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 17-3-23.
 */
public class PictureGridAdapter<T> extends BaseAdapter {
    LayoutInflater inflater;
    Context mContext;
    private List<T> dataList;
    private PicDeleteListener listener;
    private String picAddress;

    public PictureGridAdapter(Context mContext, List<T> list, PicDeleteListener listener) {
        this.mContext = mContext;
        this.listener = listener;
        this.dataList = list;
        inflater = LayoutInflater.from(mContext);
    }

    private void setDataList(List<T> list) {
        if (this.dataList == null) {
            this.dataList = new ArrayList<>();
        }
        if (list != null && list.size() > 0) {
            if (list.get(0).equals("R.drawable.add_picture2x")) {
                list.remove(0);
            }
            for (int x = 0; x < list.size(); x++) {
                if (list.get(x).equals("R.drawable.add_picture2x")) {
                    list.remove(x);
                }
            }
            this.dataList = list;
        }
        if (this.dataList.size() <= 0 || !this.dataList.get(this.dataList.size() - 1).equals("R.drawable.add_picture2x")) {
            this.dataList.add((T) "R.drawable.add_picture2x");
        }
    }

    public void setData(List<T> list) {
        setDataList(list);
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
        if (position == dataList.size() - 1) {//是最后一张图片就
            Log.e("32s", "list.size:" + dataList.size() + "picAddress:" + picAddress + "position:" + position);
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
            Log.e("32s", "list.size:" + dataList.size() + "picAddress:" + picAddress + "position:" + position);
            Picasso.with(mContext).load(new File((picAddress)))
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//加速内存的回收
                    .placeholder(R.drawable.jk_icon)//加载中
                    .error(R.drawable.jk_icon)//加载失败
                    .into(holder.imgPicTure);
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
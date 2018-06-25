package com.jinke.housekeeper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.bean.LibAllInfo;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by 32 on 2016/12/22.
 */

public class LibAllAdapter extends BaseAdapter {

    private Context mContext;
    LayoutInflater inflater;
    private List<LibAllInfo.ListObjBean> mArrayList = new ArrayList<>();
    private OnItemClickLitener mOnItemClickLitener;//监听item
    private int width = 0;
    private int heigth = 0;

    public interface OnItemClickLitener {
        void onItemClick(String keyId);
    }

    public void setData(List<LibAllInfo.ListObjBean> mArrayList) {
        this.mArrayList = mArrayList;
        notifyDataSetChanged();

    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public LibAllAdapter(List<LibAllInfo.ListObjBean> mArrayList, Context mContext) {
        this.mArrayList = mArrayList;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth() / 2;
        heigth = wm.getDefaultDisplay().getHeight() / 5;
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_lib_all, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.iv_liball);
            holder.text = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final LibAllInfo.ListObjBean bean = mArrayList.get(position);
        holder.text.setText(bean.getName());
        Glide.with(mContext).load(bean.getImgurl()).placeholder(R.drawable.lib).error(R.drawable.lib).into(holder.imageView);
        if (mOnItemClickLitener != null) {
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(bean.getId() + "");
                }
            });
        }
        holder.imageView.setLayoutParams(new LinearLayout.LayoutParams(width, heigth));
        return convertView;
    }

    ViewHolder holder;


    class ViewHolder {
        ImageView imageView;
        TextView text;
    }
}

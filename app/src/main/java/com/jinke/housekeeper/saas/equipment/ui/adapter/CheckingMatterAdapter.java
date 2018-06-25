package com.jinke.housekeeper.saas.equipment.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * function:
 * author: hank
 * date: 2017/9/8
 */

public class CheckingMatterAdapter extends RecyclerView.Adapter<CheckingMatterAdapter.ViewHolder> {
    private CheckingMatterOnClickListener listener;
    private Context mContext;
    private List<String> imgePathList;
    private int addImgeIcoPath = R.drawable.equipment_picture_add_ico;

    public CheckingMatterAdapter(Context mContext, CheckingMatterOnClickListener listener, List<String> imgePathList) {
        this.mContext = mContext;
        this.listener = listener;
        if (6 > imgePathList.size()) {
            imgePathList.add(String.valueOf(addImgeIcoPath));
        }
        this.imgePathList = imgePathList;
    }

    public void setImgePathList(List<String> imgePathList) {
        if (6 > imgePathList.size()) {
            imgePathList.add(String.valueOf(addImgeIcoPath));
        }
        this.imgePathList = imgePathList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.adapter_checking_matter, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (7 > imgePathList.size() && imgePathList.size() == position) {
            Picasso.with(mContext)
                    .load(addImgeIcoPath)
                    .into(holder.infoImage);
            holder.infoImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//            listener.checkingMatterOnClickListener((Integer) v.getTag());
                    listener.checkingMatterOnClickListener();
                }
            });
        } else {
            Picasso.with(mContext)
                    .load(imgePathList.get(position))
                    .into(holder.infoImage);
        }
        if (1 != imgePathList.size()){
            holder.deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.checkingDeleteOnClickListener(position);
                }
            });
        }
        holder.inspectionLayout.setTag(position);
    }

    @Override
    public int getItemCount() {
        return imgePathList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView infoImage;
        ImageView deleteImage;
        RelativeLayout inspectionLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            infoImage = (ImageView) itemView.findViewById(R.id.adapter_checking_matter_info_img);
            deleteImage = (ImageView) itemView.findViewById(R.id.adapter_checking_matter_info_delete);
            inspectionLayout = (RelativeLayout) itemView.findViewById(R.id.adapter_checking_matter);

        }
    }

    public interface CheckingMatterOnClickListener {
        //添加图片监听
        void checkingMatterOnClickListener();

        //点击删除按钮监听
        void checkingDeleteOnClickListener(int index);
    }
}

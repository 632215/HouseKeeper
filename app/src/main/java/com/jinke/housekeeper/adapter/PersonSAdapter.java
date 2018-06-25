package com.jinke.housekeeper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.bean.PointInfo;
import com.jinke.housekeeper.ui.widget.VerticalProgressView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 17-3-14.
 */

public class PersonSAdapter extends RecyclerView.Adapter<PersonSAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context mContext;

    private List<PointInfo> list = new ArrayList<>();

    public PersonSAdapter(Context mContext, List<PointInfo> list) {
        this.mContext = mContext;
        this.list = list;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_recycler_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.verticalProgressView.dodo(list.get(position));
        holder.month.setText(list.get(position).getMonth());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        VerticalProgressView verticalProgressView;
        TextView month;

        public ViewHolder(View itemView) {
            super(itemView);
            verticalProgressView = (VerticalProgressView) itemView.findViewById(R.id.progressView);
            month = (TextView) itemView.findViewById(R.id.month);
        }
    }

}

package com.jinke.housekeeper.saas.report.ui.adapter;

/**
 * Created by Administrator on 2017/3/21.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.db.SelfCheckingBean;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench.FileUploadingActivity;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import www.jinke.com.library.utils.LogUtils;

public class FileUpLoadAdapter extends BaseAdapter {
    SelectNumCallBack selectNumCallBack;
    private List<SelfCheckingBean> list;
    // 用来控制CheckBox的选中状况
    public static HashMap<Integer, Boolean> isSelected;
    private Context context;
    private LayoutInflater inflater = null;

    // 构造器
    public FileUpLoadAdapter(List<SelfCheckingBean> list, Context context, SelectNumCallBack selectNumCallBack) {
        this.selectNumCallBack = selectNumCallBack;
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        isSelected = new HashMap<Integer, Boolean>();
        // 初始化数据
        initDate();
    }

    // 初始化isSelected的数据
    private void initDate() {
        for (int i = 0; i < list.size(); i++) {
            getIsSelected().put(i, false);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            // 获得ViewHolder对象
            holder = new ViewHolder();
            // 导入布局并赋值给convertview
            convertView = inflater.inflate(R.layout.item_file_uploading, null);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            holder.imgView = (ImageView) convertView.findViewById(R.id.imgView);
            holder.textDescribe = (TextView) convertView.findViewById(R.id.textDescribe);
            holder.voiceLayout = (RelativeLayout) convertView.findViewById(R.id.voiceLayout);
            holder.voiceImg = (ImageView) convertView.findViewById(R.id.voiceImg);
            holder.voiceTime = (TextView) convertView.findViewById(R.id.voiceTime);
            holder.category = (TextView) convertView.findViewById(R.id.category);
            holder.keyPoints = (TextView) convertView.findViewById(R.id.keyPoints);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.deviderLine = (TextView) convertView.findViewById(R.id.deviderLine);

            // 为view设置标签
            convertView.setTag(holder);
        } else {
            // 取出holder
            holder = (ViewHolder) convertView.getTag();
        }
        SelfCheckingBean bean = list.get(position);
        if (bean != null) {
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    LogUtils.i("wanonCheckedChanged", "onCheckedChanged");
                    if (isChecked == true) {
                        isSelected.put(position, true);
                    } else {
                        isSelected.put(position, false);
                    }
                    int numSelected = 0;
                    Iterator iterator = isSelected.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry entry = (Map.Entry) iterator.next();
                        boolean tag = (boolean) entry.getValue();
                        if (tag == true) {
                            numSelected++;
                        }
                    }
                    LogUtils.i("wanonCheckedChanged", "numSelected" + numSelected);
                    selectNumCallBack.getSelectNum(numSelected);
                }
            });

            if (bean.getPictureList().size() > 0) {
                Picasso.with(context).load(new File(bean.getPictureList().get(0)))
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//加速内存的回收
                        .placeholder(R.drawable.jk_icon)//加载中
                        .error(R.drawable.jk_icon)//加载失败
                        .into(holder.imgView);
            }
            if (bean.getDecribe().equals("") || bean.getDecribe() == null) {
                if (bean.getRecordNameList().size() > 0 || bean.getRecordTimeList().size() > 0) {
                    holder.voiceLayout.setVisibility(View.VISIBLE);
                    holder.textDescribe.setVisibility(View.GONE);
                    holder.voiceTime.setText(bean.getRecordTimeList().get(0)+"″");
                } else {
                    holder.voiceLayout.setVisibility(View.GONE);
                    holder.textDescribe.setVisibility(View.GONE);
                }
            } else {
                holder.voiceLayout.setVisibility(View.GONE);
                holder.textDescribe.setVisibility(View.VISIBLE);
                holder.textDescribe.setText(bean.getDecribe());
            }

            if (null == bean.getCategory() || "".equals(bean.getCategory())){
                holder.category.setVisibility(View.INVISIBLE);
            }
            holder.category.setText(bean.getCategory());
            if (null == bean.getKeyPoint() || "".equals(bean.getKeyPoint())){
                holder.keyPoints.setVisibility(View.INVISIBLE);
            }
            holder.keyPoints.setText(bean.getKeyPoint());
            holder.time.setText(bean.getStaffTime());
            holder.time.setVisibility(View.VISIBLE);

            if (FileUploadingActivity.isInEdit) {
                holder.deviderLine.setVisibility(View.VISIBLE);
                holder.checkBox.setVisibility(View.VISIBLE);
            } else {
                holder.deviderLine.setVisibility(View.GONE);
                holder.checkBox.setVisibility(View.GONE);
            }
            // 根据isSelected来设置checkbox的选中状况
            holder.checkBox.setChecked(getIsSelected().get(position));

        }
        return convertView;
    }

    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        FileUpLoadAdapter.isSelected = isSelected;
    }

    public static class ViewHolder {
        public CheckBox checkBox;
        ImageView imgView;//图片
        TextView textDescribe;//文字描述
        RelativeLayout voiceLayout;//语音布局
        ImageView voiceImg;//语音图标
        TextView voiceTime;//语音时间
        TextView deviderLine;//分割线
        TextView category;//类别
        TextView keyPoints;//关键点位
        TextView time;//时间
    }

    public interface SelectNumCallBack {
        public void getSelectNum(int num);
    }
}
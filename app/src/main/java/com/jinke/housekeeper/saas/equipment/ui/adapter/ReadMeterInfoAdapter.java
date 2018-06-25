package com.jinke.housekeeper.saas.equipment.ui.adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.equipment.bean.ParameterBean;
import com.jinke.housekeeper.saas.equipment.ui.activity.ReadMeterInfoActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * function:
 * author: hank
 * date: 2017/11/18
 */

public class ReadMeterInfoAdapter extends BaseAdapter {

    private List<ParameterBean.ListSubjectBean> infoList;
    private Activity context;
    private LayoutInflater inflater;
    private ReadMeterInfoActivity.GetInputInfoInterface getInputInfoInterface;

    public ReadMeterInfoAdapter(Activity mContext, List<ParameterBean.ListSubjectBean> infoList, ReadMeterInfoActivity.GetInputInfoInterface getInputInfoInterface) {
        this.context = mContext;
        this.infoList = infoList;
        inflater = LayoutInflater.from(mContext);
        this.getInputInfoInterface = getInputInfoInterface;
    }

    public void setInfoListBean(List<ParameterBean.ListSubjectBean> infoList) {
        this.infoList = infoList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return infoList.size();
    }

    @Override
    public Object getItem(int i) {
        return infoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_read_meter_info, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.readMeterInfoName.setText(infoList.get(position).getSubject());
        holder.readMeterInfoValueYesterday.setText(infoList.get(position).getYesterDay()
                + infoList.get(position).getUnit());
        setPricePoint(holder.readMeterInfoValue);
        holder.readMeterInfoValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                infoList.get(position).setToday(s.toString().trim());
                getInputInfoInterface.getInputInfo(infoList);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return convertView;
    }

    ViewHolder holder;

    public class ViewHolder {
        @Bind(R.id.read_meter_info_name)
        TextView readMeterInfoName;
        @Bind(R.id.read_meter_info_value)
        EditText readMeterInfoValue;
        @Bind(R.id.read_meter_info_value_yesterday)
        TextView readMeterInfoValueYesterday;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public static void setPricePoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

        });

    }
}

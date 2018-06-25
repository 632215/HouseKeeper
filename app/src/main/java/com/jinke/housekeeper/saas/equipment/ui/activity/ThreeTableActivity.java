package com.jinke.housekeeper.saas.equipment.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.equipment.base.BaseActivity;
import com.jinke.housekeeper.saas.equipment.base.BasePresenter;
import com.jinke.housekeeper.saas.equipment.bean.AddPointBean;
import com.jinke.housekeeper.saas.equipment.bean.ParameterBean;
import com.jinke.housekeeper.saas.equipment.bean.ReadWatchBean;
import com.jinke.housekeeper.saas.equipment.config.EquipmentConfig;
import com.jinke.housekeeper.saas.equipment.precenter.ThreeTablePresenter;
import com.jinke.housekeeper.saas.equipment.ui.adapter.ThreeTableDetailsAdapter;
import com.jinke.housekeeper.saas.equipment.ui.adapter.ThreeTableEnterAdapter;
import com.jinke.housekeeper.saas.equipment.ui.widget.CustomListView;
import com.jinke.housekeeper.saas.equipment.view.ThreeTableView;
import com.jinke.housekeeper.saas.report.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

public class ThreeTableActivity extends BaseActivity<ThreeTableView, ThreeTablePresenter> implements ThreeTableView {

    @Bind(R.id.three_table_object)
    TextView threeTableObject;
    @Bind(R.id.three_table_enter_list)
    CustomListView threeTableEnterList;
    @Bind(R.id.three_table_details_list)
    CustomListView threeTableDetailsList;

    private Map<String, String> map;
    private ThreeTableEnterAdapter threeTableEnterAdapter;
    private List<ReadWatchBean.ListDataBean> enterList;
    private ThreeTableDetailsAdapter threeTableDetailsAdapter;
    private List<String> detailsList;

    @Override
    public ThreeTablePresenter initPresenter() {
        return new ThreeTablePresenter(ThreeTableActivity.this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_three_table;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.activity_three_table));
        setTitleBarBgColor(R.color.equipment_title_bg);
        setTitleColor(getResources().getColor(R.color.equipment_title_text));
        showBackwardViewIco(R.drawable.equipment_back_ico);
        threeTableObject.setText(EquipmentConfig.getProjectName());
        initAdapter();
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    private void initAdapter() {
        enterList = new ArrayList<>();
        threeTableEnterAdapter = new ThreeTableEnterAdapter(this, enterList);
        threeTableEnterList.setAdapter(threeTableEnterAdapter);
        threeTableEnterList.setOnItemClickListener(onItemClickListener);

        detailsList = new ArrayList<>();
        detailsList.add(getString(R.string.statistics_day));
        detailsList.add(getString(R.string.statistics_month));
        threeTableDetailsAdapter = new ThreeTableDetailsAdapter(this, detailsList);
        threeTableDetailsList.setAdapter(threeTableDetailsAdapter);
        threeTableDetailsList.setOnItemClickListener(detailsonItemClickListener);

        map = new HashMap<>();
        presenter.readWatch(map);
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent readMeterInfoIntent = new Intent(ThreeTableActivity.this, ReadMeterInfoActivity.class);
            readMeterInfoIntent.putExtra("deviceId", String.valueOf(enterList.get(position).getId()));
            readMeterInfoIntent.putExtra("location", String.valueOf(enterList.get(position).getPosition()));
            readMeterInfoIntent.putExtra("name", enterList.get(position).getName() == null ? "" : enterList.get(position).getName());
            ThreeTableActivity.this.startActivity(readMeterInfoIntent);
        }
    };

    private AdapterView.OnItemClickListener detailsonItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (0 == position) {
                Intent readMeterInfoIntent = new Intent(ThreeTableActivity.this, StatisticsActivity.class);
                readMeterInfoIntent.putExtra("tag", "1");
                ThreeTableActivity.this.startActivity(readMeterInfoIntent);
            } else {
                Intent readMeterInfoIntent = new Intent(ThreeTableActivity.this, StatisticsActivity.class);
                readMeterInfoIntent.putExtra("tag", "2");
                ThreeTableActivity.this.startActivity(readMeterInfoIntent);
            }
        }
    };

    /**
     * ------------ 网络请求相关方式实现began ------------
     */

    @Override
    public void readWatchSuccess(ReadWatchBean readWatchBean) {
        if (null != readWatchBean.getListData()) {
            enterList = readWatchBean.getListData();
            threeTableEnterAdapter.setInfoListBean(enterList);
        }
    }

    @Override
    public void getParameterSuccess(ParameterBean parameterBean) {

    }

    @Override
    public void completeTaskSuccess() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void onError(String msg) {

    }
    /** ------------ 网络请求相关方式实现end ------------ */

}

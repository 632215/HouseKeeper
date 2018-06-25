package com.jinke.housekeeper.saas.equipment.ui.activity;

import android.view.View;
import android.widget.AdapterView;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.equipment.base.BaseActivity;
import com.jinke.housekeeper.saas.equipment.base.BasePresenter;
import com.jinke.housekeeper.saas.equipment.bean.SetBeanList;
import com.jinke.housekeeper.saas.equipment.ui.adapter.SetAdapter;
import com.jinke.housekeeper.saas.equipment.ui.widget.CustomListView;
import com.jinke.housekeeper.saas.equipment.utils.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class LinkSetActivity extends BaseActivity {

    @Bind(R.id.link_set_list_view)
    CustomListView linkSetListView;

    private SetAdapter setAdapter;
    private SetBeanList setBeanList;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_link_set;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.activity_link_set));
        setTitleBarBgColor(R.color.equipment_title_bg);
        setTitleColor(getResources().getColor(R.color.equipment_title_text));
        showBackwardViewIco(R.drawable.equipment_back_ico);
        initAdapter();
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    private void initAdapter() {
        setBeanList = (SetBeanList) SharedPreferenceUtil.get(this, "EquipmentActivity", "SetBean");
        if (null == setBeanList) {
            List<SetBeanList.SetBean> beanList = new ArrayList<>();
            beanList.add(new SetBeanList.SetBean(getString(R.string.link_set_bluetooth), true));
            beanList.add(new SetBeanList.SetBean(getString(R.string.link_set_nfc), false));
            beanList.add(new SetBeanList.SetBean(getString(R.string.link_set_two), false));
            setBeanList = new SetBeanList(beanList);
            SharedPreferenceUtil.save(this, "EquipmentActivity", "SetBean", setBeanList);
        }
        setAdapter = new SetAdapter(this, setBeanList.getSetBeanList());
        linkSetListView.setAdapter(setAdapter);
        linkSetListView.setOnItemClickListener(onItemClickListener);
    }

    @OnClick({R.id.link_set_save})
    public void linkSetOnClickListener(View v) {
        switch (v.getId()) {
            case R.id.link_set_save:
                SharedPreferenceUtil.save(this, "EquipmentActivity", "SetBean", setBeanList);
                ToastUtils.showShort( "保存成功");
                break;
        }
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            for (int i = 0; i < setBeanList.getSetBeanList().size(); i++) {
                setBeanList.getSetBeanList().get(i).setSetState(false);
            }
            setBeanList.getSetBeanList().get(position).setSetState(true);
            setAdapter.setInfoListBean(setBeanList.getSetBeanList());
        }
    };


}

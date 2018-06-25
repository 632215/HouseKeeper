package com.jinke.housekeeper.saas.equipment.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.equipment.base.BaseFragment;
import com.jinke.housekeeper.saas.equipment.base.BasePresenter;
import com.jinke.housekeeper.saas.equipment.ui.adapter.StandBookInfoAdapter;
import com.jinke.housekeeper.saas.equipment.ui.widget.CustomListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MaintenanceFragment extends BaseFragment {

    @Bind(R.id.maintenance_list_view)
    CustomListView maintenanceListView;

    private List<Integer> infoList;
    private StandBookInfoAdapter standBookInfoAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_maintenance;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initAdapter();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    private void initAdapter() {
        infoList = new ArrayList<>();
        standBookInfoAdapter = new StandBookInfoAdapter(getActivity(),infoList);
        maintenanceListView.setAdapter(standBookInfoAdapter);
    }

}

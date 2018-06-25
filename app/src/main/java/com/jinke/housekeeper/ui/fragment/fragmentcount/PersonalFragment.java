package com.jinke.housekeeper.ui.fragment.fragmentcount;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseFragmentV4;
import com.jinke.housekeeper.bean.UserInfo;
import com.jinke.housekeeper.bean.PointInfo;
import com.jinke.housekeeper.bean.StatisticsgrInfo;
import com.jinke.housekeeper.presenter.PersonalFragmentPresenter;
import com.jinke.housekeeper.adapter.PersonSAdapter;
import com.jinke.housekeeper.view.PersonalFragmentView;
import com.jinke.housekeeper.ui.widget.LoadingLayout;
import com.jinke.housekeeper.utils.CommonlyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;

/**
 * Created by root on 17-3-14.
 */

public class PersonalFragment extends BaseFragmentV4<PersonalFragmentView, PersonalFragmentPresenter>
        implements LoadingLayout.OnReloadListener ,PersonalFragmentView{
    @Bind(R.id.rec_person)
    RecyclerView recPerson;
    @Bind(R.id.rec_project)
    RecyclerView recProject;
    @Bind(R.id.in_name)
    TextView in_name;
    PersonSAdapter adapter;
    List<PointInfo> list = new ArrayList<>();
    List<PointInfo> listProject = new ArrayList<>();
    PersonSAdapter person;
    @Bind(R.id.loadLayout)
    LoadingLayout loadLayout;
    private UserInfo userInfo;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        userInfo = CommonlyUtils.getUserInfo(getActivity());
        initTitle();
    }

    private void initTitle() {
        LinearLayoutManager liearManager = new LinearLayoutManager(getActivity());
        liearManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recPerson.setLayoutManager(liearManager);
        LinearLayoutManager man = new LinearLayoutManager(getActivity());
        man.setOrientation(LinearLayoutManager.HORIZONTAL);
        recProject.setLayoutManager(man);
        in_name.setText("巡查问题（" + userInfo.getName() + ")");
        loadLayout.setOnReloadListener(this);
    }

    @Override
    public PersonalFragmentPresenter initPresenter() {
        return new PersonalFragmentPresenter(getActivity());
    }

    public void getStatisticsgr(String userId, String sessionId) {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("userId", userId);//用户唯一标识
        map.put("sessionId", sessionId);//登陆唯一标识
        presenter.getStatisticsgr(map);
    }
    @Override
    public void getStatisticsgrNext(StatisticsgrInfo info) {
        list.clear();
        List<StatisticsgrInfo.ListObjBean> listObj = info.getListObj();
        float maxvalue = info.getMaxValue();
        loadLayout.setStatus(listObj.size() > 0 ? LoadingLayout.Success : LoadingLayout.Empty);
        for (int i = 0; i < listObj.size(); i++) {
            StatisticsgrInfo.ListObjBean bean = listObj.get(i);
            float left = (bean.getRectificationNumber() / maxvalue) * 100 > 0 ? (bean.getRectificationNumber() / maxvalue) * 100 : 0f;
            float right = (bean.getFindNumber() / maxvalue) * 100 > 0 ? (bean.getFindNumber() / maxvalue) * 100 : 0f;
            PointInfo pointInfo = new PointInfo(right, left);
            pointInfo.setMonth(bean.getMonth());
            pointInfo.setLeftText(Math.round(bean.getFindNumber()) + "");
            pointInfo.setRightText(Math.round(bean.getRectificationNumber()) + "");
            pointInfo.setRightColor(getResources().getColor(R.color.person_greay));
            pointInfo.setLeftColor(getResources().getColor(R.color.personProject));
            list.add(pointInfo);
        }
        person = new PersonSAdapter(getActivity(), list);
        recProject.setAdapter(person);
        recProject.scrollToPosition(person.getItemCount() - 1);
    }

    @Override
    public void getStatisticsgrError(String code, String msg) {
        CommonlyUtils.errorState(getActivity(), code);
    }
    public void getIndStaSponIns(String userId, String sessionId) {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("userId", userId);//用户唯一标识
        map.put("sessionId", sessionId);//登陆唯一标识
        presenter.getIndStaSponIns(map);
    }

    @Override
    public void getIndStaSponInsNext(StatisticsgrInfo info) {
        listProject.clear();
        List<StatisticsgrInfo.ListObjBean> listObj = info.getListObj();
        float maxvalue = info.getMaxValue();
        for (int i = 0; i < listObj.size(); i++) {
            StatisticsgrInfo.ListObjBean bean = listObj.get(i);
            float left = (bean.getCompleteNum() / maxvalue) * 100 > 0 ? (bean.getCompleteNum() / maxvalue) * 100 : 0f;
            float right = (bean.getPlanNum() / maxvalue) * 100 > 0 ? (bean.getPlanNum() / maxvalue) * 100 : 0f;
            PointInfo pointInfo = new PointInfo(left, right);
            pointInfo.setLeftText(Math.round(bean.getCompleteNum()) + "");
            pointInfo.setRightText(Math.round(bean.getPlanNum()) + "");
            pointInfo.setMonth(bean.getMonth());
            pointInfo.setRightColor(getResources().getColor(R.color.person_greay));
            pointInfo.setLeftColor(getResources().getColor(R.color.personf));
            listProject.add(pointInfo);
        }
        adapter = new PersonSAdapter(getActivity(), listProject);
        recPerson.setAdapter(adapter);
        recPerson.scrollToPosition(adapter.getItemCount() - 1);
    }

    @Override
    public void getIndStaSponInsError(String code, String msg) {
        CommonlyUtils.errorState(getActivity(), code);
    }


    @Override
    public void onResume() {
        super.onResume();
        loadLayout.setStatus(isConnected ? LoadingLayout.Loading : LoadingLayout.No_Network);
        getStatisticsgr(MyApplicaption.getUserId(), MyApplicaption.getSessionId());
        getIndStaSponIns(MyApplicaption.getUserId(), MyApplicaption.getSessionId());
    }

    @Override
    public void onReload(View v) {
        CommonlyUtils.setLoadConnected(loadLayout, getActivity());
        getStatisticsgr(MyApplicaption.getUserId(), MyApplicaption.getSessionId());
        getIndStaSponIns(MyApplicaption.getUserId(), MyApplicaption.getSessionId());
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

}

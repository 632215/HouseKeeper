package com.jinke.housekeeper.ui.fragment.activitymain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.base.BaseFragmentV4;
import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.adapter.KnowledgeAdapter;
import com.jinke.housekeeper.ui.activity.fragmentkonwledge.LibActivity;

import butterknife.Bind;
import www.jinke.com.library.widget.NavigationView;

/**
 * Created by Administrator on 2017/7/27.
 * 新知识库
 */

public class KnowledgeFragment extends BaseFragmentV4 implements KnowledgeAdapter.KnowledgeItemClickListener {
    @Bind(R.id.fragment_knowledge_navigationview)
    NavigationView title;
    @Bind(R.id.fragment_knowledge_recyclerview_inspection_application)
    RecyclerView inspectionRecyclerView;

    private KnowledgeAdapter inspectionAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initTitle();
        initData();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    private void initTitle() {
        title.setSaveVISIBLE(View.GONE);
        title.setBackVisible(View.GONE);
        title.setDepartLineVisible(View.GONE);
        title.setTitle(getActivity().getString(R.string.fragment_knowledge_title));
    }

    private void initData() {
        inspectionRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        inspectionAdapter = new KnowledgeAdapter(getActivity());
        inspectionRecyclerView.setAdapter(inspectionAdapter);
        inspectionAdapter.setListener(this);
    }


    @Override
    public void onKnowledgeItemClick(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(getActivity(), LibActivity.class));
                break;
            case 1:
                showToast(getString(R.string.un_realized));
                break;
            case 2:
                showToast(getString(R.string.un_realized));
                break;
            case 3:
                showToast(getString(R.string.un_realized));
                break;
            case 4:
                showToast(getString(R.string.un_realized));
                break;
        }
    }
}

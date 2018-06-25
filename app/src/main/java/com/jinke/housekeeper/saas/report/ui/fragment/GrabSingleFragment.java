package com.jinke.housekeeper.saas.report.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseFragmentV4;
import com.jinke.housekeeper.saas.report.bean.WorkOrderBean;
import com.jinke.housekeeper.saas.report.presenter.GrabSingleFragmentPresenter;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentmsg.ReportDealActivity;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench.WorkOrderDetailsActivity;
import com.jinke.housekeeper.saas.report.ui.adapter.GrabSingleAdapter;
import com.jinke.housekeeper.saas.report.ui.widget.FillListView;
import com.jinke.housekeeper.saas.report.util.NetWorksUtils;
import com.jinke.housekeeper.saas.report.util.RadioUtil;
import com.jinke.housekeeper.saas.report.util.SharedPreferencesUtils;
import com.jinke.housekeeper.saas.report.util.StringUtil;
import com.jinke.housekeeper.saas.report.view.GrabSingleFragmentView;
import com.jinke.housekeeper.ui.widget.LoadingLayout;
import com.jinke.housekeeper.utils.CommonlyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import www.jinke.com.library.pulltorefresh.PullToRefreshBase;
import www.jinke.com.library.pulltorefresh.PullToRefreshScrollView;

public class GrabSingleFragment extends BaseFragmentV4<GrabSingleFragmentView, GrabSingleFragmentPresenter> implements
        PullToRefreshBase.OnRefreshListener2<ScrollView>,
        GrabSingleFragmentView, BaseQuickAdapter.OnItemClickListener, GrabSingleAdapter.OnAudioAddressListener {

    @Bind(R.id.grab_single_list_view)
    RecyclerView grabSingleListView;
    @Bind(R.id.grab_single_load_layout)
    LoadingLayout grabSingleLoadLayout;
    @Bind(R.id.grab_single_fragment)
    PullToRefreshScrollView grabSingleFragment;

    private String typeDate = "";
    BaseQuickAdapter<WorkOrderBean.ListObjBean, BaseViewHolder> adapter;
    private List<WorkOrderBean.ListObjBean> listObjBeanList;
    private int page;
    public final static int GrabSingleCallBackCode = 2000;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_grab_single_all;
    }

    private boolean flag = false;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        grabSingleLoadLayout.setStatus(LoadingLayout.Loading);
        grabSingleLoadLayout.setOnReloadListener(onReloadListener);
        grabSingleFragment.setMode(PullToRefreshBase.Mode.BOTH);
        Bundle bundle = getArguments();
        typeDate = bundle.getString("reportType");
        grabSingleFragment.setOnRefreshListener(this);
        listObjBeanList = new ArrayList<>();


        grabSingleListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BaseQuickAdapter<WorkOrderBean.ListObjBean, BaseViewHolder>(R.layout.adapter_grab_single, listObjBeanList) {
            @Override
            protected void convert(BaseViewHolder helper, final WorkOrderBean.ListObjBean item) {
                TextView title = (TextView) helper.itemView.findViewById(R.id.tv_work_order_title);
                TextView tv_grab_time = (TextView) helper.itemView.findViewById(R.id.tv_grab_time);
                ImageView image_work_order = (ImageView) helper.itemView.findViewById(R.id.image_work_order);
                RelativeLayout rl_video_view = (RelativeLayout) helper.itemView.findViewById(R.id.voiceLayout);
                final View voiceTime = helper.itemView.findViewById(R.id.voiceTime);

                title.setText(item.getDescribe());
                tv_grab_time.setText(item.getSuperviseTime());
                rl_video_view.setVisibility(StringUtil.isEmpty(item.getAudioaddress()) ? View.GONE : View.VISIBLE);
                if (item.getImg_address().size() > 0) {
                    Glide.with(mContext)
                            .load(item.getImg_address().get(0))
                            .error(R.drawable.lib)
                            .placeholder(R.drawable.lib)
                            .into(image_work_order);
                } else {
                    //设置默认图片
                    image_work_order.setBackground(mContext.getResources().getDrawable(R.drawable.lib));
                }
                //语音播放
                rl_video_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        audioAddress(item.getAudioaddress(), voiceTime);
                    }
                });
            }
        };
        adapter.setOnItemClickListener(this);
        grabSingleListView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        listObjBeanList.clear();
        initDate(page);

    }

    @Override
    public GrabSingleFragmentPresenter initPresenter() {
        return new GrabSingleFragmentPresenter(getActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GrabSingleCallBackCode:
                if (null != data) {
                    int code = data.getIntExtra("code", 0);
                    if (code != 0) {
                        Intent grabSingleIntent = new Intent(getActivity(), ReportDealActivity.class);
                        startActivity(grabSingleIntent);
                        getActivity().finish();
                    }
                }
                break;
        }
    }


    private void initDate(int pageInfo) {
        if (NetWorksUtils.isConnected(getActivity())) {
            SortedMap<String, String> map = new TreeMap<>();
            map.put("sessionId", SharedPreferencesUtils.init(getActivity()).getString("quality_sessionId"));
            map.put("userId", MyApplicaption.getUserId());
            map.put("sceneId", "");
            map.put("reportType", typeDate);
            map.put("thirdParty", "0");
            map.put("pageInfo", CommonlyUtils.pageInfo(pageInfo));
            presenter.initDate(map);
        } else {
            grabSingleLoadLayout.setStatus(LoadingLayout.No_Network);
        }
    }


    @Override
    public void initDateOnNext(WorkOrderBean info) {
        if (null != info && null != info.getListObj()) {
            listObjBeanList.addAll(info.getListObj());
            adapter.notifyDataSetChanged();
            grabSingleLoadLayout.setStatus(listObjBeanList.size() > 0 ? LoadingLayout.Success : LoadingLayout.Empty);
        }
        grabSingleFragment.onRefreshComplete();
    }

    @Override
    public void initDateOnError(String code, String msg) {
        grabSingleLoadLayout.setStatus(LoadingLayout.Error);
        grabSingleFragment.onRefreshComplete();
        CommonlyUtils.errorState(getActivity(), code);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        page = 1;
        listObjBeanList.clear();
        initDate(page);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        page++;
        initDate(page);
    }


    private LoadingLayout.OnReloadListener onReloadListener = new LoadingLayout.OnReloadListener() {
        @Override
        public void onReload(View v) {
            page = 1;
            initDate(page);
        }
    };


    public void audioAddress(String address, View view) {
        if (flag == false) {
            flag = true;
            RadioUtil.openRadio(getActivity(), view, address, new RadioUtil.RadioPlayListener() {
                @Override
                public void onRadioPlayOver(boolean playOver) {
                    flag = playOver;
                }
            });
        } else {
            flag = false;
            RadioUtil.closeRadio(getActivity(), view);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //进入工单详情页面
        WorkOrderBean.ListObjBean bean = (WorkOrderBean.ListObjBean) adapter.getItem(position);
        Intent intent = new Intent(getActivity(), WorkOrderDetailsActivity.class);
        intent.putExtra("bean", bean);
        startActivity(intent);
    }
}

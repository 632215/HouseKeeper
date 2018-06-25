package com.jinke.housekeeper.saas.report.ui.fragment.activityreportdeal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseFragmentV4;
import com.jinke.housekeeper.saas.report.bean.WaitRectifiedBean;
import com.jinke.housekeeper.saas.report.bean.WorkOrderBean;
import com.jinke.housekeeper.saas.report.presenter.WaitRectifiedFragmentPresenter;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench.CheckDetailsActivity;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench.WaitingResponseActivity;
import com.jinke.housekeeper.saas.report.ui.widget.RecycleViewDivider;
import com.jinke.housekeeper.saas.report.util.MediaPlayerManager;
import com.jinke.housekeeper.saas.report.util.SharedPreferencesUtils;
import com.jinke.housekeeper.saas.report.util.ToastUtils;
import com.jinke.housekeeper.saas.report.view.WaitRectifiedFragmentView;
import com.jinke.housekeeper.ui.widget.LoadingLayout;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;

/**
 * 待处理
 * Created by Administrator on 2017/3/23.
 */
public class WaitRectifiedFragment extends BaseFragmentV4<WaitRectifiedFragmentView, WaitRectifiedFragmentPresenter> implements
        LoadingLayout.OnReloadListener,
        OnRefreshListener,OnLoadmoreListener,
        WaitRectifiedFragmentView, BaseQuickAdapter.OnItemClickListener {
    @Bind(R.id.fillListView)
    RecyclerView waitListView;
    @Bind(R.id.loadLayout)
    LoadingLayout loadLayout;
    @Bind(R.id.scrollView)
    SmartRefreshLayout scrollView;
    BaseQuickAdapter<WaitRectifiedBean.ListObjBean, BaseViewHolder> adapter;

    private List<WaitRectifiedBean.ListObjBean> waitRecifiedList = new ArrayList();
    private int page = 1;
    private boolean flag = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_waitrectified;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        loadLayout.setStatus(isConnected ? LoadingLayout.Loading : LoadingLayout.No_Network);
        scrollView.setOnRefreshListener(this);
        scrollView.setOnLoadmoreListener(this);
        loadLayout.setOnReloadListener(this);
        waitListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        waitListView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.home_line)));

        adapter = new BaseQuickAdapter<WaitRectifiedBean.ListObjBean, BaseViewHolder>(R.layout.item_wait_rectified, waitRecifiedList) {
            @Override
            protected void convert(BaseViewHolder helper, final WaitRectifiedBean.ListObjBean item) {
                ImageView imgState = (ImageView) helper.itemView.findViewById(R.id.imgState);
                TextView stateText = (TextView) helper.itemView.findViewById(R.id.stateText);
                ImageView imgView = (ImageView) helper.itemView.findViewById(R.id.imgView);
                TextView textDescribe = (TextView) helper.itemView.findViewById(R.id.textDescribe);
                RelativeLayout voiceLayout = (RelativeLayout) helper.itemView.findViewById(R.id.voiceLayout);
                final ImageView voiceImg = (ImageView) helper.itemView.findViewById(R.id.voiceImg);
                TextView voiceTime = (TextView) helper.itemView.findViewById(R.id.voiceTime);
                TextView category = (TextView) helper.itemView.findViewById(R.id.category);
                TextView keyPoints = (TextView) helper.itemView.findViewById(R.id.keyPoints);
                TextView time = (TextView) helper.itemView.findViewById(R.id.time);
                TextView project = (TextView) helper.itemView.findViewById(R.id.project);

                switch (item.getIsState()) {
                    //待抢单1   已抢单2  已改派3  已指派4    处理中5    已归档 9  待回访 10
                    case "1":
                        imgState.setImageResource(R.drawable.icon_work_dai_qiang_dan);
                        break;
                    case "2":
                        imgState.setImageResource(R.drawable.icon_work_qiang_dan_zhong);
                        break;
                    case "3":
                        imgState.setImageResource(R.drawable.icon_work_yi_gai_pai);
                        break;
                    case "4":
                        imgState.setImageResource(R.drawable.icon_work_yi_zhi_pai);
                        break;
                    case "5":
                        imgState.setImageResource(R.drawable.icon_work_chu_li_zhong);
                        break;
                    case "9":
                        imgState.setImageResource(R.drawable.icon_work_yi_gui_dan);
                        break;
                    case "10":
                        imgState.setImageResource(R.drawable.icon_work_hui_fan);
                        break;
                }

                if (item.getIsHangup().equals("0") && item.getIsTimeout().equals("0")) {
                    stateText.setVisibility(View.GONE);
                } else {
                    stateText.setVisibility(View.VISIBLE);
                    stateText.setText(item.getIsTimeout().equals("1") ? "超时" : item.getIsHangup().equals("Y") ? "延时" : "");
                    stateText.setTextColor(item.getIsTimeout().equals("1") ? mContext.getResources().getColor(R.color.state_red) : mContext.getResources().getColor(R.color.state_blue));
                }
                if (!StringUtils.isEmpty(item.getImg_address())) {
                    String[] pic = item.getImg_address().split("\\|");
                    Glide.with(mContext).load(pic[0]).error(R.drawable.jk_icon).placeholder(R.drawable.jk_icon).into(imgView);
                } else {
                    imgView.setImageResource(R.drawable.jk_icon);
                }

                if (item.getDescribe() == null || item.getDescribe().toString().trim() == "") {
                    textDescribe.setVisibility(View.GONE);
                    voiceLayout.setVisibility(View.VISIBLE);
                    voiceLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //播放录音

                            if (flag == false) {
                                if (!checkPermission()) {
                                    return;
                                }
                                Drawable drawable = mContext.getResources().getDrawable(R.drawable.play_anim);
                                voiceImg.setBackgroundDrawable(drawable);
                                final AnimationDrawable animation = (AnimationDrawable) voiceImg.getBackground();
                                animation.start();
                                MediaPlayerManager.playSound(item.getAudioaddress(), new MediaPlayer.OnCompletionListener() {
                                    public void onCompletion(MediaPlayer mp) {
                                        //播放完成
                                        animation.stop();
                                        voiceImg.setBackgroundResource(R.drawable.v_anim3);
                                    }
                                });
                                flag = true;
                            } else {
                                MediaPlayerManager.release();
                                flag = false;
                            }
                        }
                    });
                    voiceTime.setText(item.getAudiolen() + "″");//音频时间
                } else {
                    voiceLayout.setVisibility(View.GONE);
                    textDescribe.setVisibility(View.VISIBLE);
                    textDescribe.setText(item.getDescribe());
                }
                keyPoints.setVisibility(StringUtils.isEmpty(item.getAreaName()) ? View.GONE : View.VISIBLE);
                category.setText(item.getSceneName().equals("") ? "暂无" : item.getSceneName());
                keyPoints.setText(item.getAreaName().equals("") ? "暂无" : item.getAreaName());
                time.setText(item.getSuperviseTime().equals("") ? "暂无" : item.getSuperviseTime());
                project.setVisibility(View.GONE);

            }
        };
        waitListView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public WaitRectifiedFragmentPresenter initPresenter() {
        return new WaitRectifiedFragmentPresenter(getActivity());
    }


    //获取待整改列表

    private void getWaitList(int pageInfo) {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("sessionId", SharedPreferencesUtils.init(getActivity()).getString("quality_sessionId"));
        map.put("userId", MyApplicaption.getUserId());
        map.put("pageInfo", CommonlyUtils.pageInfo(pageInfo));
        presenter.getWaitList(map);
    }

    @Override
    public void getWaitListonNext(WaitRectifiedBean waitRectifiedBean) {
        if (waitRectifiedBean.getListObj() != null) {
            waitRecifiedList.addAll(waitRectifiedBean.getListObj());
            adapter.notifyDataSetChanged();
        }
        loadLayout.setStatus(waitRecifiedList.size() > 0 ? LoadingLayout.Success : LoadingLayout.Empty);
    }

    @Override
    public void getWaitListonError(String code, String msg) {
        showToast(msg);
        CommonlyUtils.errorState(getActivity(), code);
    }


    //任務签收
    public void getAppTaskSignData(WaitRectifiedBean.ListObjBean listObjBean) {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("userId", MyApplicaption.getUserId());
        map.put("sessionId", SharedPreferencesUtils.init(getActivity()).getString("quality_sessionId"));
        map.put("taskId", listObjBean.getTaskId());
        map.put("processId", listObjBean.getId());
        presenter.getAppTaskSignData(map);
    }

    @Override
    public void getAppTaskSignDataonError(String code, String msg) {
        showToast(msg);
        CommonlyUtils.errorState(getActivity(), code);
    }

    @Override
    public void onReload(View v) {
        CommonlyUtils.setLoadConnected(loadLayout, this.getActivity());
        waitRecifiedList.clear();
        getWaitList(page);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout){
        refreshlayout.finishRefresh(1000);
        page = 1;
        waitRecifiedList.clear();
        getWaitList(page);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        refreshlayout.finishLoadmore(1000);
        page++;
        getWaitList(page);
    }

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        waitRecifiedList.clear();
        getWaitList(page);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MediaPlayerManager.release();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //待处理的工单详情
        WaitRectifiedBean.ListObjBean listObjBean = (WaitRectifiedBean.ListObjBean) adapter.getItem(position);

        if (listObjBean.getIsState().equals("2")) {//报事预约
            WorkOrderBean.ListObjBean bean = new WorkOrderBean.ListObjBean();
            bean.setAreaName(listObjBean.getAreaName());
            bean.setAudioaddress(listObjBean.getAudioaddress());
            bean.setAudiolen(listObjBean.getAudiolen());
            bean.setDescribe(listObjBean.getDescribe());
            bean.setTaskId(listObjBean.getTaskId());
            bean.setId(listObjBean.getId());
            bean.setImg_address(listObjBean.getImg_address());
            bean.setSuperviseTime(listObjBean.getSuperviseTime());
            bean.setIsState(listObjBean.getIsState());
            bean.setOrgId(listObjBean.getOrgId());
            bean.setReportType(listObjBean.getType());
            bean.setSceneName(listObjBean.getSceneName());
            bean.setOrgname(listObjBean.getOrgname());
            bean.setPhone(listObjBean.getPhone());
            bean.setRoomNum(listObjBean.getRoomNum());
            bean.setRoomId(listObjBean.getRoomId());
            bean.setUserName(listObjBean.getSuperviseName());
            Intent intent = new Intent(getActivity(), WaitingResponseActivity.class);
            intent.putExtra("bean", bean);
            startActivity(intent);
        } else {//报事处理
            Intent intent = new Intent(getActivity(), CheckDetailsActivity.class);
            intent.putExtra("listObjBean", listObjBean);
            startActivity(intent);
        }

    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ToastUtils.showLongToast(getResources().getString(R.string.please_open_record_permision));
            return false;
        } else {
            return true;
        }
    }
}

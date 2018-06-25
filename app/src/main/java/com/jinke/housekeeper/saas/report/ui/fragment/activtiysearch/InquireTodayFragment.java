package com.jinke.housekeeper.saas.report.ui.fragment.activtiysearch;

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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.report.ui.adapter.AllReportAdapter;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseFragmentV4;
import com.jinke.housekeeper.saas.report.bean.AllReportBean;
import com.jinke.housekeeper.saas.report.bean.ProcessDetailBean;
import com.jinke.housekeeper.bean.UserInfo;
import com.jinke.housekeeper.saas.report.bean.WaitRectifiedBean;
import com.jinke.housekeeper.saas.report.presenter.InquireTodayFragmentPresenter;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench.RectProcessActivity;
import com.jinke.housekeeper.saas.report.ui.widget.FillListView;
import com.jinke.housekeeper.saas.report.ui.widget.RecycleViewDivider;
import com.jinke.housekeeper.saas.report.util.MediaPlayerManager;
import com.jinke.housekeeper.saas.report.util.SharedPreferencesUtils;
import com.jinke.housekeeper.saas.report.util.ToastUtils;
import com.jinke.housekeeper.saas.report.view.InquireTodayFragmentView;
import com.jinke.housekeeper.ui.widget.LoadingLayout;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import www.jinke.com.library.pulltorefresh.PullToRefreshBase;
import www.jinke.com.library.pulltorefresh.PullToRefreshScrollView;

/**
 * Created by pc on 2017/3/15.
 */

public class InquireTodayFragment extends BaseFragmentV4<InquireTodayFragmentView, InquireTodayFragmentPresenter> implements
        View.OnClickListener, OnRefreshListener, OnLoadmoreListener,
        LoadingLayout.OnReloadListener, InquireTodayFragmentView, BaseQuickAdapter.OnItemClickListener {
    @Bind(R.id.loadLayout)
    LoadingLayout loadLayout;
    @Bind(R.id.scrollView)
    SmartRefreshLayout scrollView;

    @Bind(R.id.fillListView)
    RecyclerView allListView;

    private UserInfo userInfo;

    private WaitRectifiedBean.ListObjBean waitBean; //发送给流程节点整改数据类
    private ProcessDetailBean.ObjBean objBeanDetail = new ProcessDetailBean.ObjBean();//节点详情传递给流程节点上端的数据类

    //    private AllReportAdapter allReportAdapter;//全部报事
    private List<AllReportBean.ListObjBean> allReportList = new ArrayList();
    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_today;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        loadLayout.setStatus(isConnected ? LoadingLayout.Loading : LoadingLayout.No_Network);
        scrollView.setOnRefreshListener(this);
        scrollView.setOnLoadmoreListener(this);
        scrollView.setOnClickListener(this);
        loadLayout.setOnReloadListener(this);
        userInfo = CommonlyUtils.getUserInfo(getActivity());
        initAdapter();
    }

    BaseQuickAdapter<AllReportBean.ListObjBean, BaseViewHolder> adapter;

    private boolean flag = false;

    private void initAdapter() {
        allListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        allListView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.home_line)));

        adapter = new BaseQuickAdapter<AllReportBean.ListObjBean, BaseViewHolder>(R.layout.item_wait_rectified, allReportList) {
            @Override
            protected void convert(BaseViewHolder helper, final AllReportBean.ListObjBean item) {
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

                if (item.getHangOrTime().equals("")) {
                    stateText.setVisibility(View.GONE);
                } else {
                    stateText.setVisibility(View.VISIBLE);
                    stateText.setText(item.getHangOrTime().equals("3") ? "超时" : item.getHangOrTime().equals("2") ? "延时" : "");
                    stateText.setTextColor(item.getHangOrTime().equals("3") ? mContext.getResources().getColor(R.color.state_red) : mContext.getResources().getColor(R.color.state_blue));
                }

                if (item.getSupervisePotoadd() != null && item.getSupervisePotoadd().toString().trim() != "" && item.getSupervisePotoadd().toString().trim().contains("|")) {
                    Picasso.with(mContext).load(item.getSupervisePotoadd().substring(0, item.getSupervisePotoadd().indexOf("|")))
                            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//加速内存的回收
                            .placeholder(R.drawable.jk_icon)//加载中
                            .error(R.drawable.jk_icon)//加载失败
                            .into(imgView);
                } else if (item.getSupervisePotoadd() != null && item.getSupervisePotoadd().toString().trim() != "") {
                    Picasso.with(mContext).load(item.getSupervisePotoadd().toString())
                            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//加速内存的回收
                            .placeholder(R.drawable.jk_icon)//加载中
                            .error(R.drawable.jk_icon)//加载失败
                            .into(imgView);
                }

                if (item.getSuperviseDescribe() == null || item.getSuperviseDescribe().toString().trim() == "") {
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
                                MediaPlayerManager.playSound(item.getAudioAdd(), new MediaPlayer.OnCompletionListener() {
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
                    voiceTime.setText(item.getAudioAddLength() + "″");//音频时间
                } else {
                    voiceLayout.setVisibility(View.GONE);
                    textDescribe.setVisibility(View.VISIBLE);
                    textDescribe.setText(item.getSuperviseDescribe());
                }
                time.setText(item.getSuperviseTime().equals("") ? "暂无" : item.getSuperviseTime());
                keyPoints.setText(item.getCruxName().equals("") ? "暂无" : item.getCruxName());
                project.setVisibility(View.GONE);
                category.setText(item.getSceneName().equals("") ? "暂无" : item.getSceneName());
            }
        };
        allListView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
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


    String startTime;
    String endTime;

    private void getTime() {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 0);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        startTime = dateString.toString().trim();
        endTime = dateString.toString().trim();
    }


    private void getAllReportList(int pageInfo) {
        getTime();
        SortedMap<String, String> map = new TreeMap<>();
        map.put("sessionId", SharedPreferencesUtils.init(getActivity()).getString("quality_sessionId"));
        map.put("userId", MyApplicaption.getUserId());
        map.put("orgId", CommonlyUtils.getUserInfo(getActivity()).getLeftOrgId());
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("pageInfo", CommonlyUtils.pageInfo(pageInfo));
        presenter.getAllReportList(map);
    }

    @Override
    public void getAllReportListonNext(AllReportBean bean) {
        if (bean != null) {
            allReportList.addAll(bean.getListObj());
            adapter.notifyDataSetChanged();
        }
        loadLayout.setStatus(allReportList.size() > 0 ? LoadingLayout.Success : LoadingLayout.Empty);
    }

    @Override
    public void getAllReportListonError(String code, String msg) {
        showToast(msg);
        CommonlyUtils.errorState(getActivity(), code);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onReload(View v) {
        CommonlyUtils.setLoadConnected(loadLayout, this.getActivity());
        allReportList.clear();
        page = 1;
        getAllReportList(page);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        refreshlayout.finishRefresh(1000);
        allReportList.clear();
        page = 1;
        getAllReportList(page);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        refreshlayout.finishLoadmore(1000);
        page++;
        getAllReportList(page);
    }

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        allReportList.clear();
        getAllReportList(page);
    }

    @Override
    public InquireTodayFragmentPresenter initPresenter() {
        return new InquireTodayFragmentPresenter(getActivity());
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        waitBean = new WaitRectifiedBean.ListObjBean();
        waitBean = new WaitRectifiedBean.ListObjBean();
        AllReportBean.ListObjBean allReport = (AllReportBean.ListObjBean) adapter.getItem(position);
        waitBean.setId(String.valueOf(allReport.getId()));//类型的强制转化
        waitBean.setIsState(String.valueOf(allReport.getIsState()));
        Intent intent = new Intent(getContext(), RectProcessActivity.class);
        if (allReport != null) {
            objBeanDetail.setHandUserName(allReport.getSuperviseName());
            objBeanDetail.setSceneName(allReport.getSceneName());
            objBeanDetail.setAreaName(allReport.getCruxName());
            objBeanDetail.setRoomNum(allReport.getRoomNum());
            if (allReport.getAudioAdd() != null || allReport.getAudioAdd() != "") {
                objBeanDetail.setAudioAdd(allReport.getAudioAdd());
                objBeanDetail.setAudioAddLen(Integer.parseInt(allReport.getAudioAddLength()));

            }
            if (allReport.getSuperviseDescribe() != null || allReport.getSuperviseDescribe() != "") {
                objBeanDetail.setSuperviseDescribe(allReport.getSuperviseDescribe());

            }
        }
        intent.putExtra("objBeanDetail", objBeanDetail);
        intent.putExtra("waitBean", waitBean);
        startActivity(intent);
    }
}
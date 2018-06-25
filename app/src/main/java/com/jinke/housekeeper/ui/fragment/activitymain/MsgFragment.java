package com.jinke.housekeeper.ui.fragment.activitymain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseFragmentV4;
import com.jinke.housekeeper.presenter.MsgFragmentPresenter;
import com.jinke.housekeeper.utils.TencentCountUtil;
import com.jinke.housekeeper.utils.TextUtils;
import com.jinke.housekeeper.view.MsgFragmentView;
import com.jinke.housekeeper.adapter.MsgAdapter;
import com.jinke.housekeeper.bean.MsgBean;
import com.jinke.housekeeper.bean.PersonalTaskBean;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench.GrabSingleActivity;
import com.jinke.housekeeper.ui.activity.fragmentuser.MemberlistActivity;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentmsg.NewsActivity;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentmsg.NoticeInfoActivity;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentmsg.ReportDealActivity;
import com.jinke.housekeeper.utils.CommonlyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.widget.NavigationView;

/**
 * Created by Administrator on 2017/7/27.
 * 新消息
 */

public class MsgFragment extends BaseFragmentV4<MsgFragmentView, MsgFragmentPresenter> implements MsgFragmentView {
    @Bind(R.id.fragment_msg_navigationview)
    NavigationView title;
    @Bind(R.id.fragment_msg_text_pending_num)
    TextView fragmentMsgTextPendingNum;
    @Bind(R.id.fragment_msg_text_processed_num)
    TextView fragmentMsgTextProcessedNum;
    @Bind(R.id.fragment_msg_text_list)
    ListView fragmentMsgTextList;
    @Bind(R.id.fragment_msg_text_hint)
    TextView fragmentMsgTextHint;
    private List<MsgBean.ListBean> msgList;
    private MsgAdapter msgAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_msg;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initTitle();
        initAdapter();
    }

    @Override
    public void onResume() {
        super.onResume();
        requestMsgList();
        requestMsgData();
    }

    @Override
    public MsgFragmentPresenter initPresenter() {
        return new MsgFragmentPresenter(getActivity());
    }

    @OnClick({R.id.fragment_msg_layout_news_more, R.id.fragment_msg_layout_problems_more
            , R.id.fragment_home_grab_single, R.id.fragment_msg_text_processed_num, R.id.fragment_msg_text_pending_num})
    public void ocClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_msg_layout_news_more:
                startActivity(new Intent(getActivity(), NewsActivity.class));
                TencentCountUtil.count(getActivity(), "fragment_msg_layout_news_more");
                break;
            case R.id.fragment_msg_layout_problems_more:
            case R.id.fragment_msg_text_pending_num:
                startActivity(new Intent(getActivity(), ReportDealActivity.class));
                TencentCountUtil.count(getActivity(), "fragment_msg_layout_problems_more");
                break;
            case R.id.fragment_msg_text_processed_num:
                Intent pendingReportDealIntent = new Intent(getActivity(), ReportDealActivity.class);
                pendingReportDealIntent.putExtra("date", "pending");
                startActivity(pendingReportDealIntent);
                TencentCountUtil.count(getActivity(), "fragment_msg_text_processed_num");
                break;
            case R.id.fragment_home_grab_single:
                startActivity(new Intent(getActivity(), GrabSingleActivity.class));
                TencentCountUtil.count(getActivity(), "fragment_home_grab_single");
                break;
        }
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if ("1".equals(msgList.get(position).getIsRead())) {
                switchActivity(position);
            } else {
                updateReadStatus(position);
            }
        }
    };

    private void initTitle() {
        title.setSaveVISIBLE(View.GONE);
        title.setBackVisible(View.GONE);
        title.setDepartLineVisible(View.GONE);
        title.setTitle(getActivity().getString(R.string.activity_main_tab_msg));
    }

    private void initAdapter() {
        msgList = new ArrayList<>();
        msgAdapter = new MsgAdapter(getActivity(), msgList);
        fragmentMsgTextList.setAdapter(msgAdapter);
        fragmentMsgTextList.setOnItemClickListener(onItemClickListener);
    }

    /**
     * 跳转Activity
     *
     * @param position
     */
    private void switchActivity(int position) {
        switch (msgList.get(position).getMsgTypeId()) {
            case "6":
                break;
            case "7":
                startActivity(new Intent(getActivity(), ReportDealActivity.class));
                break;
            case "8":
                startActivity(new Intent(getActivity(), ReportDealActivity.class));
                break;
            case "9":
                Intent infoIntent = new Intent(getActivity(), NoticeInfoActivity.class);
                infoIntent.putExtra("title", msgList.get(position).getTitle());
                infoIntent.putExtra("issuetime", msgList.get(position).getCreateTime());
                infoIntent.putExtra("url", msgList.get(position).getUrl());
                startActivity(infoIntent);
                break;
            case "10":
                startActivity(new Intent(getActivity(), MemberlistActivity.class));
                break;
            case "11":
                startActivity(new Intent(getActivity(), GrabSingleActivity.class));
                break;
            case "12":
                startActivity(new Intent(getActivity(), MemberlistActivity.class));
                break;
        }
    }

    /**
     * 请求消息列表数据
     */
    private void requestMsgList() {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("userId", MyApplicaption.getUserId());
        map.put("sessionId", MyApplicaption.getSessionId());
        map.put("pageInfo", CommonlyUtils.pageInfo(1));
        presenter.requestMsgList(map);
    }

    @Override
    public void requestMsgListNext(MsgBean info) {
        if (null != info.getList() && 0 != info.getList().size()) {
            msgList.clear();
            msgList.addAll(info.getList());
            msgAdapter.setListBean(msgList);
        } else {
            fragmentMsgTextList.setVisibility(View.GONE);
            fragmentMsgTextHint.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void requestMsgListError(String code, String msg) {
        fragmentMsgTextList.setVisibility(View.GONE);
        fragmentMsgTextHint.setVisibility(View.VISIBLE);
        showToast(msg);
        CommonlyUtils.errorState(getActivity(), String.valueOf(code));
    }

    /**
     * 请求我的问题数值数据
     */
    private void requestMsgData() {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("userId", MyApplicaption.getUserId());
        map.put("sessionId", MyApplicaption.getSessionId());
        presenter.requestMsgData(map);
    }

    @Override
    public void requestMsgDataNext(final PersonalTaskBean info) {
        if (null != info) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    fragmentMsgTextPendingNum.setText(String.valueOf(info.getAgencyNum()));
                    if (info.getAgencyNum() > 9999) {
                        TextUtils.setSmallText(fragmentMsgTextPendingNum, true, "", info.getAgencyNum() + "+");
                    }
                    fragmentMsgTextProcessedNum.setText(String.valueOf(info.getHaveDoNum()));
                    if (info.getHaveDoNum() > 99) {
                        TextUtils.setSmallText(fragmentMsgTextProcessedNum, true, "", info.getHaveDoNum() + "+");
                    }
                }
            });
        }
    }

    @Override
    public void requestMsgDataError(String code, String msg) {
        showToast(msg);
        CommonlyUtils.errorState(getActivity(), code);
    }

    /**
     * 更新消息状态
     */
    private void updateReadStatus(int position) {
        final int p = position;
        SortedMap<String, String> map = new TreeMap<>();
        map.put("userId", MyApplicaption.getUserId());
        map.put("sessionId", MyApplicaption.getSessionId());
        map.put("id", msgList.get(position).getId());
        presenter.updateReadStatus(map, p);
    }

    @Override
    public void updateReadStatusNext(int p) {
        switchActivity(p);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }
}

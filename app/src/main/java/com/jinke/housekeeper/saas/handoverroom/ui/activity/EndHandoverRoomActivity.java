package com.jinke.housekeeper.saas.handoverroom.ui.activity;

import android.view.View;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.handoverroom.base.BaseActivity;
import com.jinke.housekeeper.saas.handoverroom.bean.EmptyBean;
import com.jinke.housekeeper.saas.handoverroom.bean.HandoverRoomBean;
import com.jinke.housekeeper.saas.handoverroom.precenter.EndHandoverRoomPresenter;
import com.jinke.housekeeper.saas.handoverroom.ui.adapter.HandRoomAdapter;
import com.jinke.housekeeper.saas.handoverroom.ui.widget.CustomListView;
import com.jinke.housekeeper.saas.handoverroom.view.EndHandoverRoomView;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

import static com.jinke.housekeeper.saas.handoverroom.ui.activity.BeganHandoverRoomActivity.BEGAN_HANDOVER_ROOM;

public class EndHandoverRoomActivity extends BaseActivity<EndHandoverRoomView, EndHandoverRoomPresenter>
        implements EndHandoverRoomView {

    @Bind(R.id.end_handover_room_list)
    CustomListView endHandoverRoomList;

    private HandoverRoomBean handoverRoomBean;
    private HandRoomAdapter handRoomAdapter;


    @Override
    public EndHandoverRoomPresenter initPresenter() {
        return new EndHandoverRoomPresenter(EndHandoverRoomActivity.this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_end_handover_room;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.activity_end_handover_room));
        setTitleBarBgColor(R.color.equipment_title_bg);
        setTitleColor(getResources().getColor(R.color.equipment_title_text));
        showBackwardViewIco(R.drawable.handover_room_back_ico);

        initDate();
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @OnClick(R.id.finish_handover_room)
    public void endHandoverRoomOnClickListener(View view) {
        switch (view.getId()) {
            case R.id.finish_handover_room:
                Map<String, String> map = new HashMap<>();
                map.put("projectId", handoverRoomBean.getListData().get(0).getProjectId());
                map.put("lineNo", handoverRoomBean.getListData().get(0).getLineNo());
                map.put("userId", handoverRoomBean.getListData().get(0).getUserId());
                presenter.takeHouse(map);
                break;
        }
    }

    private void initDate() {
        if (null != getIntent().getSerializableExtra("date")) {
            handoverRoomBean = new HandoverRoomBean();
            handoverRoomBean = (HandoverRoomBean) getIntent().getSerializableExtra("date");
            handRoomAdapter = new HandRoomAdapter(EndHandoverRoomActivity.this,handoverRoomBean.getListData());
            endHandoverRoomList.setAdapter(handRoomAdapter);
        } else {
            finish();
        }
    }

    @Override
    public void takeHouseSuccess(EmptyBean emptyBean) {
        setResult(BEGAN_HANDOVER_ROOM);
        finish();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void onError(String msg) {

    }

}

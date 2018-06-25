package com.jinke.housekeeper.saas.patrol.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.patrol.base.BaseActivity;
import com.jinke.housekeeper.saas.patrol.bean.PointListBean;
import com.jinke.housekeeper.saas.patrol.config.PatrolConfig;
import com.jinke.housekeeper.saas.patrol.precenter.PatrolPresenter;
import com.jinke.housekeeper.saas.patrol.view.PaltrolView;
import com.jinke.housekeeper.utils.CommonlyUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;
import www.jinke.com.library.utils.NetWorksUtils;
import www.jinke.com.library.widget.ProgressDialog;

public class PatrolActivity extends BaseActivity<PaltrolView, PatrolPresenter> implements PaltrolView {
    private Map<String, String> map = new HashMap<>();

    @Override
    public PatrolPresenter initPresenter() {
        return new PatrolPresenter();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_patrol;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.activity_patrol));
        setTitleBarBgColor(R.color.patrol_remind_bg);
        setTitleColor(getResources().getColor(R.color.layout_title_bar_bg));
        showBackwardViewIco(R.drawable.patrol_remind_back);
        showBackwardView(R.string.empty, true);
        initDate();
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PatrolConfig.setTokenBean(null);
    }

    @OnClick({R.id.patrol_began, R.id.patrol_location, R.id.patrol_remind, R.id.patrol_statement})
    protected void functionOcClick(View view) {
        if (null != PatrolConfig.getTokenBean() && null != PatrolConfig.getTokenBean().getToken() && !"".equals(PatrolConfig.getTokenBean().getToken())) {
            switch (view.getId()) {
                case R.id.patrol_began:
                    initBluetooth();
                    break;

                case R.id.patrol_location:
                    if (NetWorksUtils.isConnected(this)) {
                        if (null != CommonlyUtils.getUserInfo(this).getLeftOrgId() && !"".equals(CommonlyUtils.getUserInfo(this).getLeftOrgId())) {
                            map.put("projectId", CommonlyUtils.getUserInfo(this).getLeftOrgId());
                            presenter.getPointList(map);
                        } else {
                            ToastUtils.showShort("获取项目失败");
                            finish();
                        }
                    } else {
                        new AlertDialog.Builder(PatrolActivity.this).setTitle("提示")//设置对话框标题
                                .setMessage("网络请求失败，请检查网络再试")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
                    break;

                case R.id.patrol_remind:
                    startActivity(new Intent(PatrolActivity.this, PatrolRemindActivity.class));
                    break;

                    //巡更报表
                case R.id.patrol_statement:
                    startActivity(new Intent(PatrolActivity.this, StatementActivity.class));
                    break;
            }
        } else {
            new AlertDialog.Builder(PatrolActivity.this).setTitle("提示")//设置对话框标题
                    .setMessage("网络请求失败，请检查网络再试")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }

    }

    @Override
    public void showMessage(String message) {
        dismissProgressDialog();
        if (null == PatrolConfig.getTokenBean() || null == PatrolConfig.getTokenBean().getToken() || "".equals(PatrolConfig.getTokenBean().getToken())) {
            new AlertDialog.Builder(PatrolActivity.this).setTitle("提示")//设置对话框标题
                    .setMessage("网络请求失败，请检查网络再试")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            dialog.dismiss();
                        }
                    }).show();
        }
    }

    @Override
    public void showLoading() {
        initProgressDialog();
    }


    @Override
    public void getPointListBean(List<PointListBean> list) {
        dismissProgressDialog();
        if (null != list && 0 != list.size()) {
            startActivity(new Intent(PatrolActivity.this, PatrolManageActivity.class));
        } else {
            if (!"1".equals(PatrolConfig.getTokenBean().getIfManage())) {
                ToastUtils.showShort( "您所在的项目还未添加巡更点位");
            } else {
                startActivity(new Intent(PatrolActivity.this, PatrolManageActivity.class));
            }
        }
    }

    @Override
    public void onError(String msg) {
        dismissProgressDialog();
        if (null == PatrolConfig.getTokenBean() || null == PatrolConfig.getTokenBean().getToken() || "".equals(PatrolConfig.getTokenBean().getToken())) {
            new AlertDialog.Builder(PatrolActivity.this).setTitle("提示")//设置对话框标题
                    .setMessage("网络请求失败，请检查网络再试")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            dialog.dismiss();
                        }
                    }).show();
        }
    }


    private void initDate() {
        String openIdBean = getIntent().getStringExtra("date");
        if (null != openIdBean) {
            map.put("openId", openIdBean);
            map.put("projectId", CommonlyUtils.getUserInfo(this).getLeftOrgId());
            presenter.getTokenData(map);
        } else {
            ToastUtils.showShort("获取信息失败，请重新获取");
            finish();
        }
    }

    private ProgressDialog pd;

    private void initProgressDialog() {
        if (pd == null) {
            pd = new ProgressDialog(this);
            pd.setCancelable(false);
            pd.setCanceledOnTouchOutside(false);
            if (!pd.isShowing()) {
                pd.show();
            }
        }
    }

    private void dismissProgressDialog() {
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1313) {
            switch (resultCode) {
                // 点击确认按钮
                case Activity.RESULT_OK: {
                    BluetoothAdapter mBtAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (!mBtAdapter.isEnabled()) {
                        ToastUtils.showShort("没有打开蓝牙，请打开蓝牙再试");
                    } else {
                        Intent patrolBeganIntent = new Intent(PatrolActivity.this, PatrolLinkActivity.class);
                        patrolBeganIntent.putExtra("label_activity", 1);
                        startActivity(patrolBeganIntent);
                    }
                }
                break;

                // 点击取消按钮或点击返回键
                case Activity.RESULT_CANCELED: {
                    ToastUtils.showShort( "没有打开蓝牙，请打开蓝牙再试");
                }
                break;
                default:
                    break;
            }
        }
    }

    private void initBluetooth() {
        if (null != BluetoothAdapter.getDefaultAdapter()) {
            if (BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                Intent patrolBeganIntent = new Intent(PatrolActivity.this, PatrolLinkActivity.class);
                patrolBeganIntent.putExtra("label_activity", 1);
                startActivity(patrolBeganIntent);
            } else {
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, 1313);
            }
        } else {
            ToastUtils.showShort( "没有检测到蓝牙模块");
        }

    }
}

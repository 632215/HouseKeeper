package com.jinke.housekeeper.saas.patrol.ui.activity;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.dk.bleNfc.BleManager.BleManager;
import com.dk.bleNfc.BleManager.Scanner;
import com.dk.bleNfc.BleManager.ScannerCallback;
import com.dk.bleNfc.BleNfcDeviceService;
import com.dk.bleNfc.DeviceManager.BleNfcDevice;
import com.dk.bleNfc.DeviceManager.ComByteManager;
import com.dk.bleNfc.DeviceManager.DeviceManager;
import com.dk.bleNfc.DeviceManager.DeviceManagerCallback;
import com.dk.bleNfc.Exception.DeviceNoResponseException;
import com.dk.bleNfc.Tool.StringTool;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.patrol.base.BaseActivity;
import com.jinke.housekeeper.saas.patrol.bean.SignPatrolListBean;
import com.jinke.housekeeper.saas.patrol.precenter.PatrolBeganPresenter;
import com.jinke.housekeeper.saas.patrol.ui.fragment.PatrolSignInFragment;
import com.jinke.housekeeper.saas.patrol.ui.fragment.PatrolSignOutFragment;
import com.jinke.housekeeper.saas.patrol.ui.widget.LoadingLayout;
import com.jinke.housekeeper.saas.patrol.utils.LogUtil;
import com.jinke.housekeeper.saas.patrol.view.PatrolBeganView;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench.ReportRegistActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import butterknife.Bind;
import www.jinke.com.library.widget.ProgressDialog;

public class PatrolBeganActivity extends BaseActivity<PatrolBeganView, PatrolBeganPresenter> implements PatrolBeganView {

    @Bind(R.id.patrol_sign_in_loading)
    LoadingLayout patrolSignInLoading;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private PatrolSignInFragment signInFragment;
    private PatrolSignOutFragment signOutFragment;
    private int label = 0;// 0签到 1 巡更中  2 签退
    private Map<String, String> map = new HashMap<>();
    private String signId = "";
    private String systemSignInTime = "";
    private BroadcastReceiver bleIndoReceiver;

    private int recordLinkTime;//记录单开连接后重新尝试次数尝试三次 不成功确定断开连接
    //蓝牙处理相关参数
    public final static String BLE_INFO_BROADCAST_ACTION = "www.jinke.com.ble.info";
    public final static String BLE_LINK_BROADCAST_ACTION = "www.jinke.com.ble.link";
    public final static String BLE_INFO_UID = "UID";
    public final static String BLE_LINK_UID = "LINK";
    private BleNfcDeviceService mBleNfcDeviceService;
    private BleNfcDevice bleNfcDevice;
    private Scanner mScanner;
    private BluetoothDevice mNearestBle;
    private Lock mNearestBleLock;// 锁对象
    private int lastRssi;
    private boolean timerIsRun;//定时器是否启动 false没有起动  true启动

    @Override
    public PatrolBeganPresenter initPresenter() {
        return new PatrolBeganPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_patrol_began;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.activity_patrol_sign_in));
        showBackwardView(R.string.empty, true);
        patrolSignInLoading.setStatus(LoadingLayout.Loading);
        initDate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
        if (mBleNfcDeviceService != null) {
            mBleNfcDeviceService.setScannerCallback(scannerCallback);
            mBleNfcDeviceService.setDeviceManagerCallback(deviceManagerCallback);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timerIsRun) {
            unbindService(mServiceConnection);
            timer.cancel();
        }
    }

    private void initDate() {
        recordLinkTime = 0;
        timerIsRun = false;
        initScanner();
        presenter.getIfSignOut(map);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    protected void onForward(View forwardView) {
        super.onForward(forwardView);
        if (1 == this.label) {

            //巡更
            Intent reportRegistIntent = new Intent(PatrolBeganActivity.this, ReportRegistActivity.class);
            reportRegistIntent.putExtra("patrol", "patrol");
            reportRegistIntent.putExtra("inspType", "123");
            startActivity(reportRegistIntent);
        }
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        signInFragment = new PatrolSignInFragment();
        signInFragment.setFragmentSwitch(fragmentSwitch);
        signInFragment.setInitDate(signId, systemSignInTime);
        signOutFragment = new PatrolSignOutFragment();
        signOutFragment.setFragmentSwitch(fragmentSwitch);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.patrol_began_body, signInFragment);
        fragmentTransaction.add(R.id.patrol_began_body, signOutFragment);
        fragmentTransaction.hide(signOutFragment);
        fragmentTransaction.show(signInFragment);
        fragmentTransaction.commit();
    }



    /**
     * 转化Fragment
     */
    private void switchFragment(String patrolSignInTime, String patrolSignOutTime, int patrolFrequencies ,int pointLeakSize) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (label) {
            case 0:
                setTitle(getString(R.string.activity_patrol_began));
                showForwardView(R.string.patrol_began_registration, true);
                showForwardViewColor(getResources().getColor(R.color.patrol_remind_bg));
                fragmentTransaction.show(signInFragment);
                fragmentTransaction.hide(signOutFragment);
                this.label = 1;
                break;

            case 1:
                showForwardView(R.string.patrol_began_registration, false);
                setTitle(getString(R.string.activity_patrol_sign_out_success));
                fragmentTransaction.show(signOutFragment);
                fragmentTransaction.hide(signInFragment);
                signOutFragment.setPatrolInfo(patrolSignInTime, patrolSignOutTime, patrolFrequencies,pointLeakSize);
                this.label = 2;
                break;
            case 2:
                finish();
                break;
        }
        fragmentTransaction.commit();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void showMessage() {
    }

    @Override
    public void onSuccess(SignPatrolListBean bean) {
        patrolSignInLoading.setStatus(LoadingLayout.Success);
        if (null != bean && null != bean.getListData() && 0 != bean.getListData().size()) {
            signId = bean.getListData().get(0).getSignId();
            systemSignInTime = bean.getListData().get(0).getSignTime();
            setTitle(getString(R.string.activity_patrol_began));
            showForwardView(R.string.patrol_began_registration, true);
            showForwardViewColor(getResources().getColor(R.color.patrol_remind_bg));
            this.label = 1;
        }
        initFragment();
    }

    public abstract class FragmentSwitch {
        public abstract void fragmentSwitch(String patrolSignInTime, String patrolSignOutTime, int patrolFrequencies,int pointLeakSize);
    }

    private FragmentSwitch fragmentSwitch = new FragmentSwitch() {
        @Override
        public void fragmentSwitch(String patrolSignInTime, String patrolSignOutTime, int patrolFrequencies,int pointLeakSize) {
            switchFragment(patrolSignInTime, patrolSignOutTime, patrolFrequencies,pointLeakSize);
        }
    };

    private void registerReceiver() {
        bleIndoReceiver = new PatrolBeganReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PatrolLinkActivity.BLE_LINK_BROADCAST_ACTION);
        registerReceiver(bleIndoReceiver, intentFilter);
    }

    private void unregisterReceiver() {
        unregisterReceiver(bleIndoReceiver);
    }

    private Boolean isShowPatrolAddDialog = true;

    private class PatrolBeganReceiver extends BroadcastReceiver {
        //接收到广播后自动调用该方法
        @Override
        public void onReceive(Context context, Intent intent) {
            if (BleManager.STATE_DISCONNECTED == intent.getIntExtra(PatrolLinkActivity.BLE_LINK_UID, -1)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //监听到蓝牙断开，退出计时器
                        if (timerIsRun) {
                            timer.cancel();
                        }
                        if (isShowPatrolAddDialog && recordLinkTime > 2) {
                            new AlertDialog.Builder(PatrolBeganActivity.this).setTitle("提示")//设置对话框标题
                                    .setMessage("设备连接断开请重新连接")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                            dialog.dismiss();
                                        }
                                    }).show();
                            isShowPatrolAddDialog = !isShowPatrolAddDialog;
                        } else {
                            if (null != mNearestBleLock) {
                                initBluetooth();
                            } else {
                                new AlertDialog.Builder(PatrolBeganActivity.this).setTitle("提示")//设置对话框标题
                                        .setMessage("设备连接断开请重新连接")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                                dialog.dismiss();
                                            }
                                        }).show();
                            }
                        }
                    }
                });
            }
        }
    }

    /**
     * ----------重新尝试连接打卡设备 began ---------------
     */
    private void initScanner() {
        lastRssi = -100;
        mNearestBle = null;
        mNearestBleLock = new ReentrantLock();
        Intent gattServiceIntent = new Intent(this, BleNfcDeviceService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBleNfcDeviceService = ((BleNfcDeviceService.LocalBinder) service).getService();
            bleNfcDevice = mBleNfcDeviceService.bleNfcDevice;
            mScanner = mBleNfcDeviceService.scanner;
            mBleNfcDeviceService.setDeviceManagerCallback(deviceManagerCallback);
            mBleNfcDeviceService.setScannerCallback(scannerCallback);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBleNfcDeviceService = null;
        }
    };

    /**
     * 设备操作类回调
     */
    private DeviceManagerCallback deviceManagerCallback = new DeviceManagerCallback() {
        @Override
        public void onReceiveConnectBtDevice(boolean blnIsConnectSuc) {
            super.onReceiveConnectBtDevice(blnIsConnectSuc);
            if (blnIsConnectSuc) {
                recordLinkTime = 0;
                LogUtil.loge("设备连接成功! ");
                timer.schedule(task, 100, 100);
                dismissProgressDialog();
                if (mNearestBle != null) {
                    LogUtil.loge("设备名称 : " + bleNfcDevice.getDeviceName());
                }
                LogUtil.loge("信号强度 : " + lastRssi);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (null == bleNfcDevice) {
                                return;
                            }
                            byte versions = bleNfcDevice.getDeviceVersions();
                            LogUtil.loge("设备版本 : " + String.format("%02x", versions));
                            double voltage = bleNfcDevice.getDeviceBatteryVoltage();
                            if (voltage < 3.61) {
                                LogUtil.loge("设备版本 : 设备电池电量低，请及时充电！ ----  " + voltage);
                            } else {
                                LogUtil.loge("设备版本 : 设备电池电量充足！ ----  " + voltage);
                            }
                            boolean isSuc = bleNfcDevice.androidFastParams(true);
                            if (isSuc) {
                                LogUtil.loge("蓝牙快速传输参数设置成功!");
                            } else {
                                LogUtil.loge("不支持快速传输参数设置!");
                            }

                            //开始自动寻卡
                            LogUtil.loge("开启自动寻卡...");
                            if (!bleNfcDevice.startAutoSearchCard((byte) 20, ComByteManager.ISO14443_P4)) {
                                LogUtil.loge("不支持自动寻卡 ");
                            }
                        } catch (DeviceNoResponseException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }

        @Override
        public void onReceiveDisConnectDevice(boolean blnIsDisConnectDevice) {
            super.onReceiveDisConnectDevice(blnIsDisConnectDevice);
        }

        @Override
        public void onReceiveConnectionStatus(boolean blnIsConnection) {
            super.onReceiveConnectionStatus(blnIsConnection);
        }

        @Override
        public void onReceiveInitCiphy(boolean blnIsInitSuc) {
            super.onReceiveInitCiphy(blnIsInitSuc);
        }

        @Override
        public void onReceiveDeviceAuth(byte[] authData) {
            super.onReceiveDeviceAuth(authData);
        }

        /**
         * 寻到卡片回调
         * @param blnIsSus
         * @param cardType
         * @param bytCardSn
         * @param bytCarATS
         */
        @Override
        public void onReceiveRfnSearchCard(boolean blnIsSus, int cardType, byte[] bytCardSn, byte[] bytCarATS) {
            super.onReceiveRfnSearchCard(blnIsSus, cardType, bytCardSn, bytCarATS);
            if (!blnIsSus || cardType == BleNfcDevice.CARD_TYPE_NO_DEFINE) {
                return;
            }
            LogUtil.loge("接收到激活卡片回调 : ");
            LogUtil.loge("UID : " + StringTool.byteHexToSting(bytCardSn));
            LogUtil.loge("ATS : " + StringTool.byteHexToSting(bytCarATS));
            Intent intent = new Intent();
            intent.setAction(BLE_INFO_BROADCAST_ACTION);
            intent.putExtra(BLE_INFO_UID, StringTool.byteHexToSting(bytCardSn));
            sendBroadcast(intent);
        }

        @Override
        public void onReceiveRfmSentApduCmd(byte[] bytApduRtnData) {
            super.onReceiveRfmSentApduCmd(bytApduRtnData);
            LogUtil.loge("接收到APDU回调 : " + StringTool.byteHexToSting(bytApduRtnData));
        }

        @Override
        public void onReceiveRfmClose(boolean blnIsCloseSuc) {
            super.onReceiveRfmClose(blnIsCloseSuc);
        }

        @Override
        //按键返回回调
        public void onReceiveButtonEnter(byte keyValue) {
            if (keyValue == DeviceManager.BUTTON_VALUE_SHORT_ENTER) { //按键短按
                LogUtil.loge("接收到按键短按回调");
            } else if (keyValue == DeviceManager.BUTTON_VALUE_LONG_ENTER) { //按键长按
                LogUtil.loge("接收到按键长按回调");
            }
        }
    };

    /**
     * Scanner 回调
     */
    private ScannerCallback scannerCallback = new ScannerCallback() {
        @Override
        public void onReceiveScanDevice(BluetoothDevice device, int rssi, byte[] scanRecord) {
            super.onReceiveScanDevice(device, rssi, scanRecord);
            //搜索蓝牙设备并记录信号强度最强的设备 从广播数据中过滤掉其它蓝牙设备
            if ((null != scanRecord) && (StringTool.byteHexToSting(scanRecord).contains("017f5450"))) {
                LogUtil.loge("搜到设备 : " + device.getName());
                LogUtil.loge("信号强度 : " + rssi);
                if (mNearestBle != null) {
                    if (rssi > lastRssi) {
                        mNearestBleLock.lock();
                        try {
                            mNearestBle = device;
                        } finally {
                            mNearestBleLock.unlock();
                        }
                    }
                } else {
                    mNearestBleLock.lock();
                    try {
                        mNearestBle = device;
                    } finally {
                        mNearestBleLock.unlock();
                    }
                    lastRssi = rssi;
                }
            }
        }

        @Override
        public void onScanDeviceStopped() {
            super.onScanDeviceStopped();
        }
    };

    /**
     * 搜索最近的设备并连接
     */
    private void searchNearestBleDevice() {
        LogUtil.loge("正在搜索设备....");
        initProgressDialog();
        if (!mScanner.isScanning() && (bleNfcDevice.isConnection() == BleManager.STATE_DISCONNECTED)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (this) {
                        mScanner.startScan(0);
                        mNearestBleLock.lock();
                        try {
                            mNearestBle = null;
                        } finally {
                            mNearestBleLock.unlock();
                        }
                        lastRssi = -100;
                        int searchCnt = 0;
                        while ((mNearestBle == null)
                                && (searchCnt < 10000)
                                && (mScanner.isScanning())
                                && (bleNfcDevice.isConnection() == BleManager.STATE_DISCONNECTED)) {
                            searchCnt++;
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        if (mScanner.isScanning() && (bleNfcDevice.isConnection() == BleManager.STATE_DISCONNECTED)) {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            mScanner.stopScan();
                            mNearestBleLock.lock();
                            try {
                                if (mNearestBle != null) {
                                    mScanner.stopScan();
                                    LogUtil.loge("正在连接设备...");
                                    bleNfcDevice.requestConnectBleDevice(mNearestBle.getAddress());
                                } else {
                                    LogUtil.loge("未找到设备！");
                                    if (isShowPatrolAddDialog && recordLinkTime > 2) {
                                        new AlertDialog.Builder(PatrolBeganActivity.this).setTitle("提示")//设置对话框标题
                                                .setMessage("设备连接断开请重新连接")
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        finish();
                                                        dialog.dismiss();
                                                    }
                                                }).show();
                                        isShowPatrolAddDialog = !isShowPatrolAddDialog;
                                    } else {
                                        initBluetooth();
                                    }
                                }
                            } finally {
                                mNearestBleLock.unlock();
                            }
                        } else {
                            mScanner.stopScan();
                        }
                    }
                }
            }).start();
        }
    }

    private Timer timer = new Timer();
    private TimerTask task = new TimerTask() {

        @Override
        public void run() {
            // 需要做的事:发送消息
            if (null != bleNfcDevice && BleManager.STATE_DISCONNECTED == bleNfcDevice.isConnection()) {
                Intent intent = new Intent();
                intent.setAction(BLE_LINK_BROADCAST_ACTION);
                intent.putExtra(BLE_LINK_UID, bleNfcDevice.isConnection());
                sendBroadcast(intent);
            }
        }
    };

    /**
     * 扫描入口
     */
    private void initBluetooth() {
        if (null != BluetoothAdapter.getDefaultAdapter()) {
            if (BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                if ((bleNfcDevice.isConnection() == BleManager.STATE_CONNECTED)) {
                    bleNfcDevice.requestDisConnectDevice();
                } else {
                    searchNearestBleDevice();
                }
            } else {
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, 1313);
            }
        } else {
            ToastUtils.showShort( "没有检测到蓝牙模块");
        }

    }

    //等待对话框
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

    /** ----------重新尝试连接打卡设备 end ---------------*/

}

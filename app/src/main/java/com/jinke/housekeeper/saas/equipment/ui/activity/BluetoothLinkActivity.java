package com.jinke.housekeeper.saas.equipment.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

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
import com.jinke.housekeeper.saas.equipment.base.BaseActivity;
import com.jinke.housekeeper.saas.equipment.base.BasePresenter;
import com.jinke.housekeeper.saas.equipment.bean.TaskBean;
import com.jinke.housekeeper.saas.equipment.utils.LogUtil;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import butterknife.OnClick;

public class BluetoothLinkActivity extends BaseActivity {

    public final static String BLE_INFO_BROADCAST_ACTION = "www.jinke.com.ble.info";
    public final static String BLE_LINK_BROADCAST_ACTION = "www.jinke.com.ble.link";
    public final static String BLE_INFO_UID = "UID";
    public String bleInfoUid ;
    public final static String BLE_LINK_UID = "LINK";
    private Dialog linkLodedialog;
    public final static int STAND_BOOK = 9212;
    public final static int DAILY_PATROL = 9210;
    public final static int DAILY_PATROL_DETAILS = 9211;
    public final static int CHECKING_INFORMATION = 9213;

    //蓝牙处理相关参数
    private BleNfcDeviceService mBleNfcDeviceService;
    private BleNfcDevice bleNfcDevice;
    private Scanner mScanner;
    private BluetoothDevice mNearestBle;
    private Lock mNearestBleLock;// 锁对象
    private int lastRssi;

    private String date;
    private TaskBean.PointListBean listBean;
    private String taskTime;
    private String taskId;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_bluetooth_link;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.activity_bluetooth_link));
        showBackwardView(R.string.empty);
        timer.schedule(task, 100, 100);
        if (null != getIntent().getStringExtra("date")) {
            date = getIntent().getStringExtra("date");
            if ("DAILY_PATROL_DETAILS".equals(date)) {
                listBean = (TaskBean.PointListBean) getIntent().getSerializableExtra("listBean");
                taskTime = getIntent().getStringExtra("taskTime");
                taskId = getIntent().getStringExtra("taskId");
            }
        } else {
            ToastUtils.showShort("数据加载失败请重新加载");
            finish();
        }
        initScanner();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBleNfcDeviceService != null) {
            mBleNfcDeviceService.setScannerCallback(scannerCallback);
            mBleNfcDeviceService.setDeviceManagerCallback(deviceManagerCallback);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (linkLodedialog.isShowing()) {
            linkLodedialog.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        timer.cancel();
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @OnClick({R.id.patrol_link})
    protected void buttonOnClock(View view) {
        switch (view.getId()) {
            case R.id.patrol_link:
                initBluetooth();
                break;
        }
    }

    private void initScanner() {
        linkLodedialog = new Dialog(this);
        linkLodedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window dialogWindow = linkLodedialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.TOP);
        lp.y = (int) getResources().getDimension(R.dimen.base_dimen_278); // 新位置Y坐标
        dialogWindow.setAttributes(lp);
        linkLodedialog.setContentView(R.layout.link_load);
        linkLodedialog.setOnCancelListener(onCancelListener);

        lastRssi = -100;
        mNearestBle = null;
        mNearestBleLock = new ReentrantLock();
        Intent gattServiceIntent = new Intent(this, BleNfcDeviceService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private DialogInterface.OnCancelListener onCancelListener = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            if (mScanner.isScanning()) {
                mScanner.stopScan();
            }
        }
    };

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
     * 设备操作类回调
     */
    private DeviceManagerCallback deviceManagerCallback = new DeviceManagerCallback() {
        @Override
        public void onReceiveConnectBtDevice(boolean blnIsConnectSuc) {
            super.onReceiveConnectBtDevice(blnIsConnectSuc);
            if (blnIsConnectSuc) {
                LogUtil.loge("设备连接成功! ");
                if (linkLodedialog.isShowing()) {
                    linkLodedialog.cancel();
                }
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
                            startAutoSearchCard();
                            if ((bleNfcDevice.isConnection() == BleManager.STATE_CONNECTED)
                                    || (bleNfcDevice.isConnection() == BleManager.STATE_CONNECTING)) {
                                if ("STAND_BOOK".equals(date)){
                                    startActivityForResult(new Intent(BluetoothLinkActivity.this, AwaitLinkActivity.class), STAND_BOOK);
                                }else if ("DAILY_PATROL".equals(date)){
                                    Intent dailyPatrolIntent = new Intent(new Intent(BluetoothLinkActivity.this, AwaitLinkActivity.class));
                                    dailyPatrolIntent.putExtra("date", "DAILY_PATROL");
                                    startActivityForResult(dailyPatrolIntent, STAND_BOOK);
                                } else if ("DAILY_PATROL_DETAILS".equals(date)){
                                    Intent dailyPatrolIntent = new Intent(new Intent(BluetoothLinkActivity.this, AwaitLinkActivity.class));
                                    dailyPatrolIntent.putExtra("date", "DAILY_PATROL_DETAILS");
                                    dailyPatrolIntent.putExtra("listBean", listBean);
                                    dailyPatrolIntent.putExtra("taskTime", taskTime);
                                    dailyPatrolIntent.putExtra("taskId", taskId);
                                    startActivityForResult(dailyPatrolIntent, STAND_BOOK);
                                }
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
            LogUtil.loge("设备断开链接!");
            if (linkLodedialog.isShowing()) {
                linkLodedialog.cancel();
            }
            new AlertDialog.Builder(BluetoothLinkActivity.this.getApplicationContext()).setTitle("提示")//设置对话框标题
                    .setMessage("设备断开链接，请重试")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                            dialog.dismiss();
                        }
                    }).show();//在按键响应事件中显示此对话框
        }

        @Override
        public void onReceiveConnectionStatus(boolean blnIsConnection) {
            super.onReceiveConnectionStatus(blnIsConnection);
            LogUtil.loge("设备链接状态回调");
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
            bleInfoUid =  StringTool.byteHexToSting(bytCardSn);
            intent.putExtra(BLE_INFO_UID, bleInfoUid);
            sendBroadcast(intent);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (bleNfcDevice.isAutoSearchCard()) {
                            //如果是自动寻卡的，寻到卡后，先关闭自动寻卡
                            bleNfcDevice.stoptAutoSearchCard();
                            //读卡结束，重新打开自动寻卡
                            startAutoSearchCard();
                        } else {
//                            isReadWriteCardSuc = readWriteCardDemo(cardTypeTemp);
                            //如果不是自动寻卡，读卡结束,关闭天线
                            bleNfcDevice.closeRf();
                        }

                    } catch (DeviceNoResponseException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
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
     * 开始自动寻卡
     * 200ms间隔，寻M1/UL卡
     *
     * @throws DeviceNoResponseException
     */
    private void startAutoSearchCard() throws DeviceNoResponseException {
        boolean isSuc = bleNfcDevice.startAutoSearchCard((byte) 20, ComByteManager.ISO14443_P4);
        if (!isSuc) {
            LogUtil.loge("不支持自动寻卡 ");
        }
    }

    /**
     * 搜索最近的设备并连接
     */
    private void searchNearestBleDevice() {
        LogUtil.loge("正在搜索设备....");
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
                                    if (linkLodedialog.isShowing()) {
                                        linkLodedialog.cancel();
                                    }
                                    if (null == mNearestBle || "".equals(mNearestBle.getAddress())) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                new AlertDialog.Builder(BluetoothLinkActivity.this).setTitle("提示")//设置对话框标题
                                                        .setMessage("没有找到相关打卡设备")
                                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                                                dialog.dismiss();
                                                            }
                                                        }).show();//在按键响应事件中显示此对话框
                                            }
                                        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1313:
                switch (resultCode) {
                    // 点击确认按钮
                    case Activity.RESULT_OK: {
                        BluetoothAdapter mBtAdapter = BluetoothAdapter.getDefaultAdapter();
                        if (!mBtAdapter.isEnabled()) {
                            ToastUtils.showShort("没有打开蓝牙，请打开蓝牙再试");
                        } else {
                            if ((bleNfcDevice.isConnection() == BleManager.STATE_CONNECTED)) {
                                bleNfcDevice.requestDisConnectDevice();
                            } else {
                                searchNearestBleDevice();
                                linkLodedialog.show();
                            }
                        }
                    }
                    break;

                    // 点击取消按钮或点击返回键
                    case Activity.RESULT_CANCELED: {
                        ToastUtils.showShort("没有打开蓝牙，请打开蓝牙再试");
                    }
                    break;
                    default:
                        break;
                }
                break;
            case STAND_BOOK:
                if (null != bleInfoUid){
                    Intent intent = new Intent();
                    intent.putExtra("date",bleInfoUid);
                    setResult(STAND_BOOK,intent);
                }
                finish();
                break;

            default:
                finish();
                break;
        }
    }

    private void initBluetooth() {
        if (null != BluetoothAdapter.getDefaultAdapter()) {
            if (BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                if ((bleNfcDevice.isConnection() == BleManager.STATE_CONNECTED)) {
                    bleNfcDevice.requestDisConnectDevice();
                } else {
                    searchNearestBleDevice();
                    linkLodedialog.show();
                }
            } else {
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, 1313);
            }
        } else {
            ToastUtils.showShort( "没有检测到蓝牙模块");
        }

    }

}

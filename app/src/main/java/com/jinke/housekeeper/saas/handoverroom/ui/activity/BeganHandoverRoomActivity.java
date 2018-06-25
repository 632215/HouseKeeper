package com.jinke.housekeeper.saas.handoverroom.ui.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.handoverroom.base.BaseActivity;
import com.jinke.housekeeper.saas.handoverroom.bean.FindStateBean;
import com.jinke.housekeeper.saas.handoverroom.bean.HandoverRoomBean;
import com.jinke.housekeeper.saas.handoverroom.precenter.BeganHandoverRoomPresenter;
import com.jinke.housekeeper.saas.handoverroom.ui.widget.CustomDialog;
import com.jinke.housekeeper.saas.handoverroom.ui.widget.zxing.camera.CameraManager;
import com.jinke.housekeeper.saas.handoverroom.ui.widget.zxing.decoding.CaptureActivityHandler;
import com.jinke.housekeeper.saas.handoverroom.ui.widget.zxing.decoding.InactivityTimer;
import com.jinke.housekeeper.saas.handoverroom.ui.widget.zxing.decoding.RGBLuminanceSource;
import com.jinke.housekeeper.saas.handoverroom.ui.widget.zxing.view.ViewfinderView;
import com.jinke.housekeeper.saas.handoverroom.view.BeganHandoverRoomView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import butterknife.Bind;
import butterknife.OnClick;

public class BeganHandoverRoomActivity extends BaseActivity<BeganHandoverRoomView, BeganHandoverRoomPresenter>
        implements BeganHandoverRoomView, SurfaceHolder.Callback {

    @Bind(R.id.began_handover_room_viewfinder)
    ViewfinderView beganHandoverRoomViewfinder;
    @Bind(R.id.began_handover_room_scanner)
    SurfaceView beganHandoverRoomScanner;
    @Bind(R.id.handover_room_project)
    TextView handoverRoomProject;
    @Bind(R.id.handover_room_sequence)
    TextView handoverRoomSequence;

    private CaptureActivityHandler handler;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private ProgressDialog mProgress;
    private String photo_path;
    private Bitmap scanBitmap;
    public static final int RESULT_CODE_QR_SCAN = 0xA1;
    private static final int REQUEST_CODE_SCAN_GALLERY = 100;
    public static final String INTENT_EXTRA_KEY_QR_SCAN = "qr_scan_result";
    private HandoverRoomBean.ListDataBean listDataBean;
    private HandoverRoomBean handoverRoomBean;
    public final static int BEGAN_HANDOVER_ROOM = 9210;

    @Override
    public BeganHandoverRoomPresenter initPresenter() {
        return new BeganHandoverRoomPresenter(BeganHandoverRoomActivity.this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_began_handover_room;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.activity_began_handover_room));
        setTitleBarBgColor(R.color.equipment_title_bg);
        setTitleColor(getResources().getColor(R.color.equipment_title_text));
        showBackwardViewIco(R.drawable.handover_room_back_ico);

        CameraManager.init(getApplication());
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceHolder surfaceHolder = beganHandoverRoomScanner.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case BEGAN_HANDOVER_ROOM:
                listDataBean = null;
                handoverRoomProject.setText("请扫描二维码");
                handoverRoomSequence.setText("请扫描二维码");
                break;
            case RESULT_OK:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.began_handover_room})
    public void beganHandoverRoomOnClickListener(View view) {
        switch (view.getId()) {
            case R.id.began_handover_room:
                if (null != listDataBean) {
                    Map<String, String> map = new HashMap<>();
                    map.put("projectId", listDataBean.getProjectId());
                    map.put("lineNo", listDataBean.getLineNo());
                    presenter.findState(map);
                } else {
                    ToastUtils.showShort( "请扫描排队二维码");
                }
                break;
        }
    }

    /**
     * 扫描二维码图片的方法
     *
     * @param path
     * @return
     */
    public Result scanningImage(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        Hashtable<DecodeHintType, String> hints = new Hashtable<>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF8"); //设置二维码内容的编码

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 先获取原大小
        scanBitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false; // 获取新的大小
        int sampleSize = (int) (options.outHeight / (float) 200);
        if (sampleSize <= 0)
            sampleSize = 1;
        options.inSampleSize = sampleSize;
        scanBitmap = BitmapFactory.decodeFile(path, options);
        RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        try {
            return reader.decode(bitmap1, hints);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Handler scan result
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        //FIXME
        if (TextUtils.isEmpty(resultString)) {
            Message m = handler.obtainMessage();
            m.what = R.id.restart_preview;
            m.obj = "restart preview";
            handler.sendMessage(m);
            Toast.makeText(BeganHandoverRoomActivity.this, "扫描失败，请重新扫描", Toast.LENGTH_SHORT).show();
        } else {
            String[] dateString = result.toString().split(";");
            if (3 == dateString.length) {
                listDataBean = new HandoverRoomBean.ListDataBean();
                listDataBean.setProjectId(dateString[0]);
                listDataBean.setUserId(dateString[1]);
                listDataBean.setLineNo(dateString[2]);

                CustomDialog.Builder builder = new CustomDialog.Builder(this);
                builder.setMessage("当前" + listDataBean.getLineNo() + "接房房号");
                builder.setTitle("提示");
                builder.setNegativeButton("重新扫描", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Message m = handler.obtainMessage();
                        m.what = R.id.restart_preview;
                        m.obj = "restart preview";
                        handler.sendMessage(m);
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Map<String, String> map = new HashMap<>();
                                map.put("projectId", listDataBean.getProjectId());
                                map.put("userId", listDataBean.getUserId());
                                map.put("lineNo", listDataBean.getLineNo());
                                presenter.getHouseInfo(map);
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            } else {
                Message m = handler.obtainMessage();
                m.what = R.id.restart_preview;
                m.obj = "restart preview";
                handler.sendMessage(m);
                Toast.makeText(BeganHandoverRoomActivity.this, "无效二维码", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
        }
    }

    public ViewfinderView getViewfinderView() {
        return beganHandoverRoomViewfinder;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        beganHandoverRoomViewfinder.drawViewfinder();
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };


    @Override
    public void handleHouseSuccess() {
        Intent endHandoverRoomIntent = new Intent(BeganHandoverRoomActivity.this, EndHandoverRoomActivity.class);
        endHandoverRoomIntent.putExtra("date", handoverRoomBean);
        startActivityForResult(endHandoverRoomIntent, BEGAN_HANDOVER_ROOM);
    }

    @Override
    public void getHouseInfoSuccess(HandoverRoomBean handoverRoomBean) {
        if (null != handoverRoomBean && null != handoverRoomBean.getListData() && 0 < handoverRoomBean.getListData().size()) {
            this.handoverRoomBean = handoverRoomBean;
            for (HandoverRoomBean.ListDataBean dataBean : handoverRoomBean.getListData()) {
                dataBean.setLineNo(listDataBean.getLineNo());
            }
            handoverRoomProject.setText(this.handoverRoomBean.getListData().get(0).getProjectName());
            handoverRoomSequence.setText(this.handoverRoomBean.getListData().get(0).getLineNo());
        } else {
            Message m = handler.obtainMessage();
            m.what = R.id.restart_preview;
            m.obj = "restart preview";
            handler.sendMessage(m);
            Toast.makeText(BeganHandoverRoomActivity.this, "获取排队信息失败", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void findStateSuccess(FindStateBean findStateBean) {
        switch (findStateBean.getHouseState()){
            case "A":
                Map<String, String> map = new HashMap<>();
                map.put("projectId", listDataBean.getProjectId());
                map.put("lineNo", listDataBean.getLineNo());
                map.put("userId", listDataBean.getUserId());
                presenter.handleHouse(map);
                break;
            case "B":
                Intent endHandoverRoomIntent = new Intent(BeganHandoverRoomActivity.this, EndHandoverRoomActivity.class);
                endHandoverRoomIntent.putExtra("date", handoverRoomBean);
                startActivityForResult(endHandoverRoomIntent, BEGAN_HANDOVER_ROOM);
                break;
            case "Z":
                CustomDialog.Builder builder = new CustomDialog.Builder(this);
                builder.setMessage("当前房间已经接房");
                builder.setTitle("提示");
                builder.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Message m = handler.obtainMessage();
                                m.what = R.id.restart_preview;
                                m.obj = "restart preview";
                                handler.sendMessage(m);
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void onError(String msg) {
        Message m = handler.obtainMessage();
        m.what = R.id.restart_preview;
        m.obj = "restart preview";
        handler.sendMessage(m);
    }
}

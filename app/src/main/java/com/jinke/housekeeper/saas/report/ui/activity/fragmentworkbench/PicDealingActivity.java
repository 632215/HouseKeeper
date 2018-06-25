package com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.base.BasePresenter;
import com.js.photosdk.crop.CropImageView;
import com.js.photosdk.operate.OperateUtils;
import com.js.photosdk.scrawl.DrawAttribute;
import com.js.photosdk.scrawl.DrawingBoardView;
import com.js.photosdk.scrawl.ScrawlTools;
import com.js.photosdk.utils.FileUtils;
import com.js.photosdk.utils.PhotoUtils;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by pc on 2017/3/10.
 */
public class PicDealingActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    @Bind(R.id.rotate_left)
    TextView rotateLeft;
    @Bind(R.id.rotate_rigth)
    TextView rotateRigth;
    @Bind(R.id.rotate_view)
    LinearLayout rotateView;
    @Bind(R.id.functionclane)
    ImageView functionclane;
    private String photoPath;//图片路径
    private String tempPath;//图片路径
    @Bind(R.id.reback)
    TextView reback;//返回到拍照界面
    @Bind(R.id.sure)
    TextView sure;//保存修改并返回
    @Bind(R.id.drawLayout)
    LinearLayout drawLayout;//涂鸦区
    @Bind(R.id.cutLayout)
    RelativeLayout cutLayout;//剪切区
    @Bind(R.id.rotateLayout)
    RelativeLayout rotateLayout;//旋转区
    @Bind(R.id.detailFunction)
    LinearLayout detailFunction;//详细的功能区（颜色）
    @Bind(R.id.functionLeft)
    ImageView functionLeft;
    @Bind(R.id.functionRight)
    ImageView functionRight;
    @Bind(R.id.graffitiPic)
    TextView graffitiPic;//涂鸦
    @Bind(R.id.cutPic)
    TextView cutPic;//剪切
    @Bind(R.id.rotatePic)
    TextView rotatePic;//旋转
    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    @Bind(R.id.drawView)
    DrawingBoardView drawView;//涂鸦控件
    @Bind(R.id.cropmageView)
    CropImageView cropmageView;//剪切控件
    @Bind(R.id.picture)
    ImageView picture;//旋转控件
    private String graffitiPath;
    private String cutPath;
    private String rotatePath;
    private ScrawlTools casualWaterUtil = null;
    private List<String> pathList;
    private String lastPath = null;
    boolean isChangeed = false;
    private String path = null;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_picdealing;
    }

    //图片源文件
    private Bitmap sourceBtm;

    private void initData() {
        photoPath = getIntent().getStringExtra("photoPath");
        path = photoPath;
        graffitiPath = photoPath;
        cutPath = photoPath;
        rotatePath = photoPath;
        tempPath = photoPath;
        lastPath = photoPath;
        pathList = new ArrayList<>();
    }

    @Override
    public void initView() {
        initData();
        functionLeft.setOnClickListener(this);
        functionRight.setOnClickListener(this);
        reback.setOnClickListener(this);
        sure.setOnClickListener(this);
        graffitiPic.setOnClickListener(this);
        cutPic.setOnClickListener(this);
        rotatePic.setOnClickListener(this);
        rotateLeft.setOnClickListener(this);
        functionclane.setOnClickListener(this);
        rotateRigth.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        setDefaultRadio();
    }

    private void setDefaultRadio() {
        radioGroup.check(R.id.graffitiPic);
        lastPath = photoPath;
        graffitiPath = lastPath;
        rotatePath = lastPath;
        cutPath = lastPath;
        pathList.add(lastPath);
        functionGraff(sourceBtm);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.functionclane:
                graffitiPath = tempPath;
                rotatePath = tempPath;
                cutPath = tempPath;
                functionGraff(sourceBtm);
                break;
            case R.id.reback:
                finish();
                break;
            case R.id.sure:
                pictureResult();
                break;
            //涂鸦红色
            case R.id.functionLeft:
                Bitmap paintLeft = BitmapFactory.decodeResource(this.getResources(), R.drawable.crayon);
                casualWaterUtil.creatDrawPainter(DrawAttribute.DrawStatus.PEN_WATER, paintLeft, 0xffe9013b);
                break;
            //涂鸦黄色
            case R.id.functionRight:
                Bitmap paintBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.crayon);
                casualWaterUtil.creatDrawPainter(DrawAttribute.DrawStatus.PEN_WATER, paintBitmap, 0xffF3D40A);
                break;
            //向右旋转
            case R.id.rotate_rigth:
                bit = PhotoUtils.rotateImage(bit, 90);
                picture.setImageBitmap(bit);
                break;
            //向左旋转
            case R.id.rotate_left:
                bit = PhotoUtils.rotateImage(bit, -90);
                picture.setImageBitmap(bit);
                break;
        }
    }

    public void pictureResult() {
        isChangeed = true;
        Bitmap graffBit = null, cutBit = null;
        //涂鸦
        if (radioGroup.getCheckedRadioButtonId() == R.id.graffitiPic) {
            graffBit = casualWaterUtil.getBitmap();
            if (graffBit != null) {
                lastPath = FileUtils.saveImg(graffBit, 100);
                graffitiPath = lastPath;
                rotatePath = lastPath;
                cutPath = lastPath;
                pathList.add(lastPath);
                functionGraff(graffBit);
            }
            //旋转
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.rotatePic) {
            lastPath = FileUtils.saveImg(bit, 100);
            rotatePath = lastPath;
            cutPath = lastPath;
            graffitiPath = lastPath;
            pathList.add(lastPath);
            functionRotate(sourceBtm);
            //剪切
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.cutPic) {
            cutBit = cropmageView.getCroppedImage();
            lastPath = FileUtils.saveImg(cutBit, 100);
            cutPath = lastPath;
            graffitiPath = lastPath;
            rotatePath = lastPath;
            pathList.add(lastPath);
            functionCut(cutBit);
        }
        if (isChangeed == true) {//修改过
            for (int x = 0; x < pathList.size() - 1; x++) {
                FileUtils.deleteFile(pathList.get(x));
                pathList.remove(x);
            }
            lastPath = pathList.get(pathList.size() - 1).toString().trim();
        }
        Intent intent = new Intent();
        intent.putExtra("lastPath", lastPath);
        setResult(RESULT_OK, intent);
        finish();
    }

    public Bitmap setPicState(String state) {
        Bitmap bitmap = null;
        switch (state) {
            case "graffitiPic":
                if (casualWaterUtil != null)
                    bitmap = casualWaterUtil.getBitmap();
                break;
            case "cutPic":
                if (cropmageView != null)
                    bitmap = cropmageView.getCroppedImage();
                break;
            case "rotatePic":
                bitmap = bit;
                break;
            default:
                //获取图片源文件
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;//图片宽高都为原来的二分之一，即图片为原来的四分之一
                bitmap = BitmapFactory.decodeFile(tempPath, options);
        }
        return bitmap;
    }

    private String statePic = "none";

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            //图片标注
            case R.id.graffitiPic:
                rotateView.setVisibility(View.GONE);
                cutBit = null;
                functionLeft.setVisibility(View.VISIBLE);
                functionRight.setVisibility(View.VISIBLE);
                detailFunction.setVisibility(View.VISIBLE);
                drawLayout.setVisibility(View.VISIBLE);
                cutLayout.setVisibility(View.GONE);
                rotateLayout.setVisibility(View.GONE);
                sourceBtm = setPicState(statePic);
                functionGraff(sourceBtm);
                statePic = "graffitiPic";
                break;
            //图片剪切
            case R.id.cutPic:
                rotateView.setVisibility(View.GONE);
                graffBit = null;
                detailFunction.setVisibility(View.INVISIBLE);
                drawLayout.setVisibility(View.GONE);
                cutLayout.setVisibility(View.VISIBLE);
                rotateLayout.setVisibility(View.GONE);
                //得到涂鸦过后的图片
                graffBit = setPicState(statePic);
                functionCut(graffBit);
                statePic = "cutPic";
                break;
            //图片旋转
            case R.id.rotatePic:
                rotateView.setVisibility(View.VISIBLE);
                graffBit = null;
                cutBit = null;
                detailFunction.setVisibility(View.VISIBLE);
                detailFunction.setVisibility(View.GONE);
                drawLayout.setVisibility(View.GONE);
                cutLayout.setVisibility(View.GONE);
                rotateLayout.setVisibility(View.VISIBLE);
                Bitmap rot = setPicState(statePic);
                functionRotate(rot);
                statePic = "rotatePic";
                break;
        }
    }

    //涂鸦
    private Bitmap graffBit;
    //切图
    private Bitmap cutBit = null;
    //旋转
    private Bitmap bit = null;

    private void functionGraff(@NonNull Bitmap graffBit) {
        if (graffBit == null)
            return;
        OperateUtils operateUtils = new OperateUtils(this);
        Bitmap resizeBmp = operateUtils.compressionFiller(graffBit, drawLayout);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(resizeBmp.getWidth(), resizeBmp.getHeight());
        drawView.setLayoutParams(layoutParams);
        casualWaterUtil = new ScrawlTools(this, drawView, resizeBmp);
        Bitmap paintBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.crayon);
        casualWaterUtil.creatDrawPainter(DrawAttribute.DrawStatus.PEN_WATER, paintBitmap, 0xffe9013b);
    }

    private void functionCut(Bitmap sourceBtm) {
        OperateUtils operateUtils = new OperateUtils(this);
        Bitmap cutBit = operateUtils.compressionFiller(sourceBtm, drawLayout);
        cropmageView.setImageBitmap(cutBit);
        cropmageView.setGuidelines(cropmageView.OVER_SCROLL_ALWAYS);// 触摸时显示网格
        cropmageView.setFixedAspectRatio(false);// 自由剪切
        cropmageView.setFixedAspectRatio(true);
        cropmageView.setAspectRatio(3, 3);
    }

    private void functionRotate(Bitmap bitmap) {
        OperateUtils operateUtils = new OperateUtils(this);
        Bitmap rorateBit = operateUtils.compressionFiller(bitmap, drawLayout);
        bit = rorateBit;
        picture.setImageBitmap(rorateBit);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(PicDealingActivity.this);
        StatService.trackBeginPage(PicDealingActivity.this, "图片操作");
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(PicDealingActivity.this);
        StatService.trackEndPage(PicDealingActivity.this, "图片操作");
    }
}
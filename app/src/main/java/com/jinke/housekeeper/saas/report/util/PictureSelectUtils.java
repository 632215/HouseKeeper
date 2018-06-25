package com.jinke.housekeeper.saas.report.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.report.service.listener.PictureSelectListener;
import com.tencent.stat.StatService;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017/9/14.
 */

public class PictureSelectUtils {
    /*标识常量*/
    /* 用来标识请求相册功能的activity */
    public static final int ALBUM_WITH_DATA = 10010;
    /* 用来标识照片处理过后的返回参数*/
    public static final int PIC_DEALED = 32;

    public static void initCamera(Context mContext) {
        ImageLoader loader = new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        };

        ImgSelConfig config = new ImgSelConfig.Builder(mContext, loader)
                // 是否多选
                .multiSelect(false)
                .btnText("Confirm")
                // 确定按钮背景色
                // 确定按钮文字颜色
                .btnTextColor(Color.WHITE)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#3F51B5"))
                // 返回图标ResId
                .backResId(R.drawable.arrow_white)
                .title("Images")
                .titleColor(Color.WHITE)
                .titleBgColor(Color.parseColor("#3F51B5"))
                .allImagesText("All Images")
                .needCrop(false)
                .cropSize(1, 1, 200, 200)
                // 第一个是否显示相机
                .needCamera(true)
                // 最大选择图片数量
                .maxNum(1)
                .build();
        ImgSelActivity.startActivity((Activity) mContext, config, ALBUM_WITH_DATA);
    }

    public static void initFragmentCamera(Fragment fragment) {
        ImageLoader loader = new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        };

        ImgSelConfig config = new ImgSelConfig.Builder(fragment.getActivity(), loader)
                // 是否多选
                .multiSelect(false)
                .btnText("Confirm")
                // 确定按钮背景色
                // 确定按钮文字颜色
                .btnTextColor(Color.WHITE)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#3F51B5"))
                // 返回图标ResId
                .backResId(R.drawable.arrow_white)
                .title("Images")
                .titleColor(Color.WHITE)
                .titleBgColor(Color.parseColor("#3F51B5"))
                .allImagesText("All Images")
                .needCrop(false)
                .cropSize(1, 1, 200, 200)
                // 第一个是否显示相机
                .needCamera(true)
                // 最大选择图片数量
                .maxNum(1)
                .build();
        ImgSelActivity.startActivity(fragment, config, ALBUM_WITH_DATA);
    }

    public static void dealActivityResult(Context mContext, int requestCode, int resultCode, Intent data, ArrayList<String> pictureList, final PictureSelectListener listener) {
        try {
            if (resultCode != RESULT_OK) {
                return;
            }
            switch (requestCode) {
                case PictureSelectUtils.PIC_DEALED:
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 3;//图片宽高都为原来的二分之一，即图片为原来的四分之一
                    Bitmap graffBit = BitmapFactory.decodeFile(data.getStringExtra("lastPath"), options);
                    if (graffBit == null) {
                        return;
                    }
                    //添加图片
                    if (pictureList.size() > 0) {
                        if (pictureList.get(pictureList.size() - 1).equals("R.mipmap.add_picture2x")) {
                            pictureList.add(pictureList.size() - 1, data.getStringExtra("lastPath"));
                        }

                    } else {
                        pictureList.add(data.getStringExtra("lastPath"));
                        pictureList.add("R.mipmap.add_picture2x");
                    }
                    listener.setPicRefurbishData(pictureList);
                    break;
                case PictureSelectUtils.ALBUM_WITH_DATA:
                    if (data == null)
                        return;
                    final List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
                    String imgUrl = pathList.get(0);
                    final File file = new File(imgUrl);
                    if (file.exists()) {
                        Luban.with(mContext)
                                .load(file)                     //传人要压缩的图片
                                .setCompressListener(new OnCompressListener() { //设置回调
                                    @Override
                                    public void onStart() {
                                    }

                                    @Override
                                    public void onSuccess(File file) {
                                        listener.setPhotoData(file.getAbsolutePath());
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                    }
                                }).launch();
                    } else {
                        ToastUtils.showShort(mContext, "文件损坏，请重新选择");
                    }
                    break;
                default:
                    break;
            }
        } catch (ArithmeticException e) {
            StatService.reportException(mContext, e);
        }
    }

    /**
     * 图片压缩处理
     * @param mContext
     * @param requestCode
     * @param resultCode
     * @param data
     * @param pictureList
     * @param listener
     */
    public static void dealStartActivityResult(Context mContext, int requestCode, int resultCode, Intent data, ArrayList<String> pictureList, final PictureSelectListener listener) {
        try {
            if (resultCode != RESULT_OK) {
                return;
            }
            switch (requestCode) {
                //添加图片
                case PictureSelectUtils.PIC_DEALED:
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 3;//图片宽高都为原来的二分之一，即图片为原来的四分之一
                    Bitmap graffBit = BitmapFactory.decodeFile(data.getStringExtra("lastPath"), options);
                    if (graffBit == null) {
                        return;
                    }
                    //添加图片
                    pictureList.add(data.getStringExtra("lastPath"));
                    listener.setPicRefurbishData(pictureList);
                    break;

                    //压缩文件
                case PictureSelectUtils.ALBUM_WITH_DATA:
                    if (data == null)
                        return;
                    final List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
                    String imgUrl = pathList.get(0);
                    final File file = new File(imgUrl);
                    if (file.exists()) {
                        Luban.with(mContext)
                                .load(file)//传人要压缩的图片
                                .setCompressListener(new OnCompressListener() { //设置回调
                                    @Override
                                    public void onStart() {
                                    }

                                    @Override
                                    public void onSuccess(File file) {
                                        listener.setPhotoData(file.getAbsolutePath());
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                    }
                                }).launch();
                    } else {
                        ToastUtils.showShort(mContext, "文件损坏，请重新选择");
                    }
                    break;
                default:
                    break;
            }
        } catch (ArithmeticException e) {
            StatService.reportException(mContext, e);
        }
    }
}

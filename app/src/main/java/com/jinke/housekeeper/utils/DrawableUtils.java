package com.jinke.housekeeper.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jinke.housekeeper.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by root on 19/11/16.
 */

public class DrawableUtils {

    public static void setTextDrawable(TextView textView, Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    public static void setTextTopDrawable(TextView textView, Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, drawable, null, null);
    }
    public static void  setPicImageView(Context mContext, String url , ImageView imageView,int defu){
        Picasso.with(mContext).load(url).placeholder(defu).error(defu).into(imageView);

    }

    public static void setTextRightDrawable(TextView textView, Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, null, drawable, null);
    }

    public static void setImageView(SimpleDraweeView draweeView, String path) {
        Uri uri = Uri.parse(path);
        draweeView.setImageURI(uri);
    }

    public static void saveBitmapImage(Bitmap logoBitmap, String fileName, Handler handler) {

        File f = new File(Environment.getExternalStorageDirectory(),fileName);
        String filePath=f.getPath();
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        logoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
            Message message=new Message();
            message.what=200;
            message.obj=filePath;
            handler.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

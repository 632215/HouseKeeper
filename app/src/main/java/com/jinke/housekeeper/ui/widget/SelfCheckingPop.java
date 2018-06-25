package com.jinke.housekeeper.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jinke.housekeeper.R;


/**
 * Created by 32 on 2016/12/28.
 */

public class SelfCheckingPop extends PopupWindow implements View.OnClickListener{
    private RelativeLayout selectroot;
    private TextView camera;
    private TextView album;
    private LayoutInflater inflater;
    private Context mContext;
    private PopupWindow mPopupWindow;
    OnSelectCallBackListener mOnSelectCallBackListener;
    private Intent intent;
    public void setOnSelectCallBackListener (OnSelectCallBackListener onSelectCallBackListener){
        this.mOnSelectCallBackListener= onSelectCallBackListener;
    }


    public SelfCheckingPop(Context mContext) {
        this.mContext = mContext;
        inflater= LayoutInflater.from(mContext);
        initView();
    }

    private void initView() {
        View rootView = inflater.inflate(R.layout.dialog_pic_select, null);
        mPopupWindow = new PopupWindow(rootView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,true);
        selectroot = (RelativeLayout) rootView.findViewById(R.id.pic_select_root);
        camera= (TextView) rootView.findViewById(R.id.tv_selfchecking_dialog_camera);
        album= (TextView) rootView.findViewById(R.id.tv_selfchecking_dialog_album);
        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(mContext.getResources().getDrawable(android.R.color.transparent));
        selectroot.setOnClickListener(this);
        camera.setOnClickListener(this);
        album.setOnClickListener(this);
    }

    public void dismitPopWindow() {

        if (mPopupWindow != null&&mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    public void showPopWindow(View v) {
        if (mPopupWindow != null&&!mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_selfchecking_dialog_camera:
                System.out.println("camera");
                mOnSelectCallBackListener.camera();
                break;
            case R.id.tv_selfchecking_dialog_album:
                System.out.println("album");

                mOnSelectCallBackListener.album();
                break;
            case R.id.pic_select_root:
                dismitPopWindow();
                break;
        }
    }

    public interface OnSelectCallBackListener{
        void camera();
        void album();
    }
}
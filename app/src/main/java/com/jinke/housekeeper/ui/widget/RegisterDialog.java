package com.jinke.housekeeper.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jinke.housekeeper.R;

/**
 * Created by 32 on 2016/12/26.
 */

public class RegisterDialog extends Dialog implements View.OnClickListener{
    public TextView mKnow;
    public TextView mTitle;
    public TextView mContent;
    public OnRegisterDialogListener mListener;
    public String tips;
    public String contents;


    public void setmKnow(TextView mKnow) {
        this.mKnow = mKnow;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public RegisterDialog(Context context, OnRegisterDialogListener onRegisterDialogListener) {
        super(context,R.style.RegisterDialog);
    }

    public RegisterDialog(Context context, int themeResId, OnRegisterDialogListener mListener) {
        super(context, themeResId);
        this.mListener = mListener;
    }

    public RegisterDialog(Context context, boolean cancelable, OnCancelListener cancelListener, OnRegisterDialogListener mListener) {
        super(context, cancelable, cancelListener);
        this.mListener = mListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_register);
        initView();

    }

    private void initView() {
        mTitle = (TextView) findViewById(R.id.tv_register_dialog_title);
        mContent = (TextView) findViewById(R.id.tv_register_dialog_content);
        mKnow = (TextView) findViewById(R.id.tv_register_dialog_know);
        mKnow.setOnClickListener(this);
        mTitle.setText(tips);
        mContent.setText(contents);
    }

    @Override
    public void onClick(View view) {
        mListener.onClick();
    }

    public interface OnRegisterDialogListener {
       public void onClick();
    }
}

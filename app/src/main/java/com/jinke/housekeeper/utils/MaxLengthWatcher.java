package com.jinke.housekeeper.utils;

import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by 32 on 2017/1/17.
 */

/*
 * 监听输入内容是否超出最大长度，并设置光标位置
 * */
public class MaxLengthWatcher implements TextWatcher {

    private int maxLen = 0;
    private EditText editText = null;
    private TextView tips = null;
    private int editStart ;
    private int editEnd ;

    public MaxLengthWatcher(int maxLen, EditText editText,TextView tips) {
        this.maxLen = maxLen;
        this.editText = editText;
        this.tips =tips;
    }

    public void afterTextChanged(Editable arg0) {
        // TODO Auto-generated method stub

    }

    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {
        // TODO Auto-generated method stub

    }

    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        Editable editable = editText.getText();
        editStart = editText.getSelectionStart();
        editEnd =editText.getSelectionEnd();
        int len = editable.length();
        tips.setText("100/"+(100-len));
        if(len > maxLen)
        {
            editable.delete(editStart-1,editEnd);
            int tempSelection = editStart;
            editText.setText(editable);
            editText.setSelection(tempSelection);
            tips.setText("输入字符超过规定数量，无法输入！");
        }
    }

}

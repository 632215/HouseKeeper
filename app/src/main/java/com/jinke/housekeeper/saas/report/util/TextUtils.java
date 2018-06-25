package com.jinke.housekeeper.saas.report.util;

import android.text.Html;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/8/3.
 */

public class TextUtils {
    /**
     * 一个字符串strings：字体大小/颜色/字符串
     *
     * @param text
     * @param strings 1 - 字符串1；2 - 字符串2 颜色；3 - 字符串2 ；4 — 字符串2 大小
     */
    public static void setText(TextView text, String... strings) {
        switch (strings.length) {
            case 4://该变大小和颜色
                text.setText(Html.fromHtml(strings[0] + "<font 'color='" + strings[1] + "'><big>" + strings[2] + "</big></font>"));
                break;
            case 3://改变颜色
                text.setText(Html.fromHtml(strings[0] + "<font color='" + strings[1] + "'>" + strings[2] + "</font>"));
        }
    }

    public static void setSmallText(TextView text, boolean b, String... strings) {
        if (b) {
            text.setText(Html.fromHtml(strings[0]+"<font><small>" + strings[1] + "</small></font>"));
        }
    }
}

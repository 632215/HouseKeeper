package com.jinke.housekeeper.saas.report.util;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;

import com.jinke.housekeeper.R;

import java.io.File;

/**
 * Created by Administrator on 2017/9/14.
 */

public class RadioUtil {
    private static Drawable drawable; //音频动画图片
    private static AnimationDrawable animation;//音频动画

    public static void closeRadio(Context context, View voiceAnim) {//关闭音频
        if (animation != null && voiceAnim != null) {
            animation.stop();
            voiceAnim.setBackgroundResource(R.drawable.v_anim3);
            MediaPlayerManager.pause();
            MediaPlayerManager.release();
        }
    }

    public static void openRadio(final Context context, final View voiceAnim, String voiceFileName, final RadioPlayListener listener) { //打开音频
        if (voiceAnim != null) {
            drawable = context.getResources().getDrawable(R.drawable.play_anim);
            voiceAnim.setBackgroundDrawable(drawable);
            animation = (AnimationDrawable) voiceAnim.getBackground();
            animation.start();
            MediaPlayerManager.playSound(voiceFileName, new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    //播放完成
                    animation.stop();
                    listener.onRadioPlayOver(false);
                    voiceAnim.setBackgroundResource(R.drawable.v_anim3);
                }
            });
        }
    }

    public interface RadioPlayListener {
        void onRadioPlayOver(boolean flag);
    }
}

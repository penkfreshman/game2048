package com.example.game2048;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

public class backgoudSound {
    private static backgoudSound soundPoolUtil;
    private SoundPool soundPool;

    //单例模式
    public static backgoudSound getInstance(Context context) {
        if (soundPoolUtil == null)
            soundPoolUtil = new backgoudSound(context);
        return soundPoolUtil;
    }

    private backgoudSound(Context context) {
        soundPool = new SoundPool(3, AudioManager.STREAM_SYSTEM, 0);
        //加载音频文件



    }

    public void play(int number) {
        Log.d("tag", "number " + number);
        //播放音频
        soundPool.play(number, 1, 1, 0, 0, 1);
    }

}

package com.example.game2048;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

import java.util.HashMap;

public class backgoudSound {
    private static backgoudSound backgoud_Sound;
    private SoundPool soundPool;
    private HashMap <Integer,Integer> soundmap=new HashMap<Integer,Integer>();

    //单例模式
    public static backgoudSound getInstance(Context context) {
        if (backgoud_Sound == null)
            backgoud_Sound = new backgoudSound(context);
        return backgoud_Sound;
    }

    private backgoudSound(Context context) {
        soundPool = new SoundPool(3, AudioManager.STREAM_SYSTEM, 0);
        //加载音频文件
        if(Build.VERSION.SDK_INT > 21){
            SoundPool.Builder builder = new SoundPool.Builder();
            //传入音频数量
            builder.setMaxStreams(5);
            //AudioAttributes是一个封装音频各种属性的方法
            AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
            //设置音频流的合适的属性
            attrBuilder.setLegacyStreamType(AudioManager.STREAM_SYSTEM);//STREAM_MUSIC
            //加载一个AudioAttributes
            builder.setAudioAttributes(attrBuilder.build());
            soundPool = builder.build();
        }else{
            soundPool = new SoundPool(6, AudioManager.STREAM_SYSTEM, 0);
        }
        //
        soundmap.put(1, soundPool.load(context, R.raw.swiper_to_play, 1));
        soundmap.put(2, soundPool.load(context, R.raw.select_switch_game, 1));
        soundmap.put(3, soundPool.load(context, R.raw.merge, 1));
        soundmap.put(4, soundPool.load(context, R.raw.game_failed, 1));
        soundmap.put(5, soundPool.load(context, R.raw.unenable, 1));
        soundmap.put(6, soundPool.load(context, R.raw.button, 1));
       // soundmap.put(5, soundPool.load(context, R.raw., 1));
    }

    public void play(int number) {
      //  Log.d("tag", "number " + number);
        //播放音频
        soundPool.play(soundmap.get(number), 1, 1, 0, 0, 1);
    }
    public void  release(){
        soundPool.release();
    }

}

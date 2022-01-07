package com.example.game2048;


import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Card extends FrameLayout {
    FrameLayout.LayoutParams lp;
    private View backgroud;
    private  TextView tv;
    private  int num=0;

    public Card(@NonNull Context context) {
        super(context);
        backgroud=  new View(getContext());
        addView(backgroud,lp);

        tv =new TextView(getContext());
        tv.setTextSize(28);
        tv.setGravity(Gravity.CENTER);

        lp=new LayoutParams(-1,-1);
        lp.setMargins(10,10,0,0);
        addView(tv,lp);

    }

    public   int getNum(){
        return num;
    }

    public  void setNum(int Num){
        if (num<=0) {
            tv.setText("");
        }else{
            tv.setText(num+"");
        }

        switch (num) {
            case 0:
                tv.setBackgroundColor(0x00000000);
                break;
            case 2:
                tv.setBackgroundColor(0xffeee4da);
                break;
            case 4:
                tv.setBackgroundColor(0xffede0c8);
                break;
            case 8:
                tv.setBackgroundColor(0xfff2b179);
                break;
            case 16:
                tv.setBackgroundColor(0xfff59563);
                break;
            case 32:
                tv.setBackgroundColor(0xfff67c5f);
                break;
            case 64:
                tv.setBackgroundColor(0xfff65e3b);
                break;
            case 128:
                tv.setBackgroundColor(0xffedcf72);
                break;
            case 256:
                tv.setBackgroundColor(0xffedcc61);
                break;
            case 512:
                tv.setBackgroundColor(0xffedc850);
                break;
            case 1024:
                tv.setBackgroundColor(0xffedc53f);
                break;
            case 2048:
                tv.setBackgroundColor(0xffedc22e);
                break;
            default:
                tv.setBackgroundColor(0xff3c3a32);
                break;
        }

    }

}

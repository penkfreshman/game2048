

package com.example.game2048.Game;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.game2048.Tool.Config;
import com.example.game2048.Music.BackgoudSound;
import com.example.game2048.R;
import com.example.game2048.Tool.Tool;


public class Card extends FrameLayout {
    private TextView label;
    private View background;
    private boolean tag64=true;
    private  boolean tag512=true;
    private  boolean tag1024=true;

    public Card(Context context) {
        super(context);

        LayoutParams lp = null;

        background = new View(getContext());
        lp = new LayoutParams(-1, -1);
        lp.setMargins(20, 20, 0, 0);
        background.setBackground(getResources().getDrawable(R.drawable.card_bg));
        addView(background, lp);

        label = new TextView(getContext());
        label.setTextColor(getResources().getColor(R.color.Textcolor));
        label.setTextSize(28);
        label.setGravity(Gravity.CENTER);

        lp = new LayoutParams(-1, -1);
        lp.setMargins(20, 20, 0, 0);
        addView(label, lp);

        setNum(0);
    }


    private int num = 0;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;

        if (num<=0) {
            label.setText("");
        }else{
            label.setText(num+"");
        }

        switch (num) {
            case 0:
                label.setBackgroundColor(0x00000000);
                break;
            case 2:
                label.setBackground(getResources().getDrawable(R.drawable.bg_2));
                break;
            case 4:
                label.setBackground(getResources().getDrawable(R.drawable.bg_4));

                break;
            case 8:
                label.setBackground(getResources().getDrawable(R.drawable.bg_8));
                break;
            case 16:
                label.setBackground(getResources().getDrawable(R.drawable.bg_16));
                break;
            case 32:
                label.setBackground(getResources().getDrawable(R.drawable.bg_32));
                break;
            case 64:
                label.setBackground(getResources().getDrawable(R.drawable.bg_64));
                if(tag64&& Tool.FIRST_To_64[Config.NUM-3]){
                MainActivity.getMainActivity().getAnimLayer().create64(this);
                BackgoudSound.getInstance(getContext()).play(7);
                tag64=false;
                Tool.FIRST_To_64[Config.NUM-3]=false;
                }
                break;
            case 128:
                label.setBackground(getResources().getDrawable(R.drawable.bg_128));

                break;
            case 256:
                label.setBackground(getResources().getDrawable(R.drawable.bg_256));

                break;
            case 512:
                label.setBackground(getResources().getDrawable(R.drawable.bg_512));
                if(tag512&& Tool.FIRSR_TO_512[Config.NUM-3]){
                    MainActivity.getMainActivity().getAnimLayer().paly_animation(getContext());
                    BackgoudSound.getInstance(getContext()).play(8);
                    tag512=false;
                    Tool.FIRSR_TO_512[Config.NUM-3]=false;
                }
                break;
            case 1024:
                label.setBackground(getResources().getDrawable(R.drawable.bg_1024));

                if(tag1024&& Tool.FIRSR_TO_1024[Config.NUM-3]){
                    MainActivity.getMainActivity().getAnimLayer().play_animation1024();
                    BackgoudSound.getInstance(getContext()).play(9);
                    tag1024=false;
                    Tool.FIRSR_TO_1024[Config.NUM-3]=false;
                }
                break;
            case 2048:
                label.setBackground(getResources().getDrawable(R.drawable.bg_2048));

                break;
            default:
                label.setBackgroundColor(0xff3c3a32);
                break;
        }
    }

    public boolean equals(Card o) {
        return getNum()==o.getNum();
    }

    protected Card clone(){
        Card c= new Card(getContext());
        c.setNum(getNum());
        return c;
    }

    public TextView getLabel() {
        return label;
    }


}

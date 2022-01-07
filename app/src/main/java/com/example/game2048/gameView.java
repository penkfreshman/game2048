package com.example.game2048;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class gameView extends GridView {
    public int NUM=4;
    public static int CARD_WIDTH=0;
    public gameView(Context context) {
        super(context);
        initLayout();
    }

    public gameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout();
    }

    public gameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }
    public void initLayout(){
        setNumColumns(NUM);
        Card c=new Card(getContext());
        setBackgroundColor(getResources().getColor(R.color.theme2048));

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        CARD_WIDTH=(Math.min(w,h)-10)/NUM;
        addCard(CARD_WIDTH,CARD_WIDTH);
    }

    public  void addCard(int widht ,int height ){
            for(int x=0;x<NUM;x++){
                for(int y=0;y<NUM ;y++){
                    Card c=new Card(getContext());
                    c.setNum(2);
                    addView(c,widht,height);
                }

            }
    }
}

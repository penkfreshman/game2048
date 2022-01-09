package com.example.game2048;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;

public class gameView extends GridLayout {
    private static int NUM=4;
    private static int CARD_WIDTH=0;

    public gameView(Context context) {
        super(context);

        initGameView();
    }

    public gameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initGameView();
    }

    private void initGameView(){
       setColumnCount(NUM);
       setBackgroundColor(getResources().getColor(R.color.theme2048));
       addCards(GetCardWidth(),GetCardWidth());

        setOnTouchListener(new OnTouchListener() {
            private float startX,startY,offsetX,offsetY;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:{
                        startX=motionEvent.getX();
                        startY=motionEvent.getY();

                        break;}
                    case MotionEvent.ACTION_UP:{
                        offsetX=motionEvent.getX()-startX;
                        offsetY=motionEvent.getY()-startY;
                        if(Math.abs(offsetX)>Math.abs(offsetY)){
                            if(offsetX<-5) SwipeLeft();
                            else if (offsetX>5) SwipeRight();
                        }else{
                            if(offsetY<-5) Swipeup();
                            else if (offsetY>5) Swipedwon();
                        }

                        break;
                    }
                }
                return true;
            }
        });
    }

    private void Swipedwon() {
        Log.d("gameview","down");
    }

    private void Swipeup() {
        Log.d("gameview","up");
    }

    private void SwipeRight() {
        Log.d("gameview","right");
    }

    private void SwipeLeft() {
        Log.d("gameview","left");
        
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);


    }

    private int GetCardWidth()
    {

        //屏幕信息的对象
        DisplayMetrics displayMetrics;
        displayMetrics = getResources().getDisplayMetrics();

        //获取屏幕信息
        int cardWidth;
        cardWidth = displayMetrics.widthPixels;

        cardWidth=cardWidth-40;
        Log.d("height1",cardWidth+" ");
        GridLayout gridLayout=findViewById(R.id.gameView);
        GridLayout.LayoutParams params= new LayoutParams();
        params.height=cardWidth;
        params.width=cardWidth;
        gridLayout.setLayoutParams(params);

        Log.d("width_layout",cardWidth+"");
        //一行有四个卡片，每个卡片占屏幕的四分之一
        return  (cardWidth/NUM)-20;

    }



    private void addCards(int cardWidth,int cardHeight){

        Log.d("width",cardWidth+"");
        Card c;
        for (int y = 0; y < NUM; y++) {
            for (int x = 0; x < NUM; x++) {
                c = new Card(getContext());
                c.setNum(2);
                addView(c,cardWidth,cardHeight);

                cardsMap[x][y] = c;
            }
        }
    }


    private Card[][] cardsMap = new Card[NUM][NUM];
    private List<Point> emptyPoints = new ArrayList<Point>();
}

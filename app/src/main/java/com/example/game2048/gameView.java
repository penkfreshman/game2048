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
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;

public class gameView extends GridLayout {
    public static int NUM=4;
    public static int CARD_WIDTH=0;

    private Card[][] cardsMap = new Card[NUM][NUM];
    private List<Point> emptyPoints = new ArrayList<Point>();

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
        DisplayMetrics displayMetrics;
        displayMetrics = getResources().getDisplayMetrics();
        int cardWidth;
        cardWidth = displayMetrics.widthPixels;
        FrameLayout fl=findViewById(R.id.panel);
       setMinimumHeight(cardWidth-40);


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

        boolean merge = false;

        for (int x = 0; x < NUM; x++) {
            for (int y = NUM-1; y >=0; y--) {

                for (int y1 = y-1; y1 >=0; y1--) {
                    if (cardsMap[x][y1].getNum()>0) {

                        if (cardsMap[x][y].getNum()<=0) {
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(cardsMap[x][y1],cardsMap[x][y], x, x, y1, y);
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);

                            y++;
                            merge = true;
                        }else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(cardsMap[x][y1],cardsMap[x][y], x, x, y1, y);
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }

                        break;
                    }
                }
            }
        }

        if (merge) {
            addRandomNum();
            checkComplete();
        }

    }

    private void Swipeup() {

        boolean merge = false;

        for (int x = 0; x < NUM; x++) {
            for (int y = 0; y < NUM; y++) {

                for (int y1 = y+1; y1 <NUM; y1++) {
                    if (cardsMap[x][y1].getNum()>0) {

                        if (cardsMap[x][y].getNum()<=0) {
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(cardsMap[x][y1],cardsMap[x][y], x, x, y1, y);
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);

                            y--;

                            merge = true;
                        }else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(cardsMap[x][y1],cardsMap[x][y], x, x, y1, y);
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }

                        break;

                    }
                }
            }
        }

        if (merge) {
            addRandomNum();
            checkComplete();
        }

    }

    private void SwipeRight() {
        boolean merge = false;

        for (int y = 0; y < NUM; y++) {
            for (int x = NUM-1; x >=0; x--) {

                for (int x1 = x-1; x1 >=0; x1--) {
                    if (cardsMap[x1][y].getNum()>0) {

                        if (cardsMap[x][y].getNum()<=0) {
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(cardsMap[x1][y], cardsMap[x][y],x1, x, y, y);
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);

                            x++;
                            merge = true;
                        }else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(cardsMap[x1][y], cardsMap[x][y],x1, x, y, y);
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }

                        break;
                    }
                }
            }
        }

        if (merge) {
            addRandomNum();
            checkComplete();
        }

    }

    private void SwipeLeft() {
        boolean merge = false;

        for (int y = 0; y < NUM; y++) {
            for (int x = 0; x < NUM; x++) {

                for (int x1 = x+1; x1 < NUM; x1++) {
                    if (cardsMap[x1][y].getNum()>0) {

                        if (cardsMap[x][y].getNum()<=0) {

                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(cardsMap[x1][y],cardsMap[x][y], x1, x, y, y);

                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);

                            x--;
                            merge = true;

                        }else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(cardsMap[x1][y], cardsMap[x][y],x1, x, y, y);
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);

                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }

                        break;
                    }
                }
            }
        }

        if (merge) {
            addRandomNum();
            checkComplete();
        }
        
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

        cardWidth=cardWidth-60;
        Log.d("height1",cardWidth+" ");
     /*   GridLayout gridLayout=findViewById(R.id.gameView);
        GridLayout.LayoutParams params= new LayoutParams();
        params.height=cardWidth;
        params.width=cardWidth;
        gridLayout.setLayoutParams(params);

      */

        Log.d("width_layout",cardWidth+"");
        //一行有四个卡片，每个卡片占屏幕的四分之一
        return  (cardWidth/NUM);

    }



    private void addCards(int cardWidth,int cardHeight){

        Log.d("width",cardWidth+"");
        Card c;
        for (int y = 0; y < NUM; y++) {
            for (int x = 0; x < NUM; x++) {
                c = new Card(getContext());

                addView(c,cardWidth,cardHeight);

                cardsMap[x][y] = c;
            }
        }
    }

    private void   addRandomNum(){
        emptyPoints.clear();

        for (int y = 0; y < NUM; y++) {
            for (int x = 0; x < NUM; x++) {
                if (cardsMap[x][y].getNum()<=0) {
                    emptyPoints.add(new Point(x, y));
                }
            }
        }

        if (emptyPoints.size()>0) {

            Point p = emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
            cardsMap[p.x][p.y].setNum(Math.random()>0.1?2:4);

            MainActivity.getMainActivity().getAnimLayer().createScaleTo1(cardsMap[p.x][p.y]);
        }
    }
    private void checkComplete(){

        boolean complete = true;

        ALL:
        for (int y = 0; y < NUM; y++) {
            for (int x = 0; x < NUM; x++) {
                if (cardsMap[x][y].getNum()==0||
                        (x>0&&cardsMap[x][y].equals(cardsMap[x-1][y]))||
                        (x<NUM-1&&cardsMap[x][y].equals(cardsMap[x+1][y]))||
                        (y>0&&cardsMap[x][y].equals(cardsMap[x][y-1]))||
                        (y<NUM-1&&cardsMap[x][y].equals(cardsMap[x][y+1]))) {

                    complete = false;
                    break ALL;
                }
            }
        }

        if (complete) {
            new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("游戏结束").setPositiveButton("重新开始", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                }
            }).show();
        }

    }
    public void startGame(){

        MainActivity aty = MainActivity.getMainActivity();
        aty.clearScore();
        aty.showBestScore(aty.getScore());

        for (int y = 0; y < NUM; y++) {
            for (int x = 0; x <NUM; x++) {
                cardsMap[x][y].setNum(0);
            }
        }

        addRandomNum();
        addRandomNum();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        startGame();
    }
}

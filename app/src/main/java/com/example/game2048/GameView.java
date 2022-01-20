package com.example.game2048;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

public class GameView extends GridLayout {



    private Card[][] cardsMap = new Card[Config.NUM][Config.NUM];
    private List<Point> emptyPoints = new ArrayList<Point>();
    public int[][] card_save = new int[Config.NUM][Config.NUM];
    public int[][] card_save1 = new int[Config.NUM][Config.NUM];
    public   int score[]=new int[2];
    public   int step=0;


    MainActivity.Myhandler myhandler=new MainActivity.Myhandler(new WeakReference(MainActivity.getMainActivity()));

    public GameView(Context context) {
        super(context);

        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initGameView();
    }

    private void initGameView(){


       setColumnCount(Config.NUM);

       setBackground(getResources().getDrawable(R.drawable.bg));
      // Log.d("lifecycle",1+"");
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
                            if(offsetX<-5) {

                                SwipeLeft();
                            saveLayout();
                                step++;
                            }
                            else if (offsetX>5) {
                               // backgoudSound.getInstance(getContext()).play(1);
                                SwipeRight();
                            saveLayout();
                                step++;

                            }
                        }else{
                            if(offsetY<-5) {
                               // backgoudSound.getInstance(getContext()).play(1);
                                Swipeup();saveLayout();
                                step++;
                            }
                            else if (offsetY>5) {
                              //  backgoudSound.getInstance(getContext()).play(1);
                                Swipedwon();saveLayout();
                                step++;

                            }
                        }
                        if(step==1)
                            myhandler.sendEmptyMessage(0x123);
                        break;
                    }
                }

                return true;
            }
        });

    }

    private void Swipedwon() {

        boolean merge = false;

        for (int x = 0; x < Config.NUM; x++) {
            for (int y = Config.NUM-1; y >=0; y--) {

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
                            BackgoudSound.getInstance(getContext()).play(3);
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

        for (int x = 0; x < Config.NUM; x++) {
            for (int y = 0; y < Config.NUM; y++) {

                for (int y1 = y+1; y1 < Config.NUM; y1++) {
                    if (cardsMap[x][y1].getNum()>0) {

                        if (cardsMap[x][y].getNum()<=0) {
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(cardsMap[x][y1],cardsMap[x][y], x, x, y1, y);
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y--;

                            merge = true;
                        }else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(cardsMap[x][y1],cardsMap[x][y], x, x, y1, y);
                            BackgoudSound.getInstance(getContext()).play(3);
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

        for (int y = 0; y < Config.NUM; y++) {
            for (int x = Config.NUM-1; x >=0; x--) {

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
                            BackgoudSound.getInstance(getContext()).play(3);
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

        for (int y = 0; y < Config.NUM; y++) {
            for (int x = 0; x < Config.NUM; x++) {

                for (int x1 = x+1; x1 < Config.NUM; x1++) {
                    if (cardsMap[x1][y].getNum()>0) {

                        if (cardsMap[x][y].getNum()<=0) {

                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(cardsMap[x1][y],cardsMap[x][y], x1, x, y, y);

                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);

                            x--;
                            merge = true;

                        }else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(cardsMap[x1][y], cardsMap[x][y],x1, x, y, y);
                            BackgoudSound.getInstance(getContext()).play(3);
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

        Config.CARD_WIDTH = displayMetrics.widthPixels;

        Config.CARD_WIDTH= Config.CARD_WIDTH-60;
        Config.CARD_WIDTH= Config.CARD_WIDTH/ Config.NUM;

        //一行有四个卡片，每个卡片占屏幕的四分之一
        return Config.CARD_WIDTH;

    }



    private void addCards(int cardWidth,int cardHeight){

        //Log.d("width",cardWidth+"");
        Card c;
        for (int y = 0; y < Config.NUM; y++) {
            for (int x = 0; x < Config.NUM; x++) {
                c = new Card(getContext());
                addView(c,cardWidth,cardHeight);
                cardsMap[x][y] = c;
                       // Log.d("previous1",y+","+x+"    "+card_save1[x][y]+"");

            }
        }
    }

    private void   addRandomNum(){
        emptyPoints.clear();

        for (int y = 0; y < Config.NUM; y++) {
            for (int x = 0; x < Config.NUM; x++) {
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
        for (int y = 0; y < Config.NUM; y++) {
            for (int x = 0; x < Config.NUM; x++) {
                if (cardsMap[x][y].getNum()==0||
                        (x>0&&cardsMap[x][y].equals(cardsMap[x-1][y]))||
                        (x< Config.NUM-1&&cardsMap[x][y].equals(cardsMap[x+1][y]))||
                        (y>0&&cardsMap[x][y].equals(cardsMap[x][y-1]))||
                        (y< Config.NUM-1&&cardsMap[x][y].equals(cardsMap[x][y+1]))) {

                    complete = false;
                    break ALL;
                }
            }
        }

        if (complete) {
            BackgoudSound.getInstance(getContext()).BG_stop();
            BackgoudSound.getInstance(getContext()).play(4);
            new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("游戏结束").setPositiveButton("重新开始", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                }
            }).show();
        }

    }
    public void startGame(){
        myhandler.sendEmptyMessage(0x111);
        MainActivity aty = MainActivity.getMainActivity();
        aty.clearScore();
        aty.showBestScore(aty.getScore());

        for (int y = 0; y < Config.NUM; y++) {
            for (int x = 0; x < Config.NUM; x++) {
                cardsMap[x][y].setNum(0);
            }
        }

        addRandomNum();
        addRandomNum();
        saveLayout();
       step=0;
        refresh_music();
        Tool.FIRST_To_64[Config.NUM-3]=true;
        Tool.FIRSR_TO_512[Config.NUM-3]=true;
        Tool.FIRSR_TO_1024[Config.NUM-3]=true;

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(Tool.is_Destory[Config.NUM-3]==true)
        { loadLayout();
        Tool.is_Destory[Config.NUM-3]=false;
        }
        else
        startGame();
        //Log.d("lifecaycle",2+"");
    }

    public void saveLayout(){
        for (int y = 0; y< Config.NUM; y++)
            for (int x = 0; x< Config.NUM; x++) {
            card_save[x][y] = card_save1[x][y];
            };
        for (int y = 0; y< Config.NUM; y++) {
            for (int x = 0; x < Config.NUM; x++) {
                card_save1[x][y] = cardsMap[x][y].getNum();

            }
        }
        score[0]=score[1];

        score[1] = MainActivity.getMainActivity().getreturnScore();
    }

    public void loadLayout(){
        SharedPreferences sp= MainActivity.getMainActivity().getApplicationContext().getSharedPreferences(Tool.Save_Layout+ Config.NUM,Context.MODE_PRIVATE);
        for (int y = 0; y< Config.NUM; y++) {
            for (int x = 0; x < Config.NUM; x++) {
                card_save1[x][y] = sp.getInt(Tool.Save_Item1 + x + y, 0);
                card_save[x][y] = sp.getInt(Tool.Save_Item+ x + y, 0);
               cardsMap[x][y].setNum(card_save1[x][y]);

            }
        }
        if(sp.getInt(Tool.Step,0)<1)
            myhandler.sendEmptyMessage(0x111);

        score[0] =sp.getInt(Tool.Save_Score,0);
        score[1]=sp.getInt(Tool.Save_Score1,0);
        MainActivity.getMainActivity().showScore(score[1]);
        MainActivity.getMainActivity().showBestScore(MainActivity.getMainActivity().getScore());
        refresh_music();

    }
    public  void previous(){
            for (int y = 0; y < Config.NUM; y++){
                for (int x = 0; x < Config.NUM; x++) {
                    cardsMap[x][y].setNum(card_save[x][y]);
                    card_save1[x][y] =cardsMap[x][y].getNum();
                                       // Log.d("previous3",y+","+x+"    "+card_save[x][y]+"");
                }}

        score[1]=score[0];
            MainActivity.getMainActivity().updata_Score(score[0]);
        MainActivity.getMainActivity().showScore(score[0]);
       // Log.d("score1",score[0]+"");
    }
    public void refresh_music(){
        BackgoudSound.getInstance(getContext()).BG_stop();
        if(Tool.FLAG_TO_CONTROL_MUSIC)
            BackgoudSound.getInstance(getContext()).play_backgroud(getContext());

    }


}

package com.example.game2048.Game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.game2048.Anime.Anime;
import com.example.game2048.Tool.Config;
import com.example.game2048.Music.BackgoudSound;
import com.example.game2048.R;
import com.example.game2048.Tool.Tool;
import com.necer.ndialog.ConfirmDialog;

import java.lang.ref.WeakReference;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static MainActivity mainActivity=null;
    private TextView bt_score_tv;
    private  TextView score_tv;
    private ImageButton Restart;
    public   ImageButton previous;
    private GameView gv_layout;
    private Anime am_layout;
    private  int SCORE=0;
    private FrameLayout fl;
    private int  max=0;
    private  ImageView imageView;
    private ConstraintLayout layout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //backgoudSound.getInstance(this);
        game_to_switch();
        setContentView(R.layout.activity_main);
        initButton();
        mainActivity=this;

        if (Tool.FLAG_TO_CONTROL_MUSIC)
            BackgoudSound.getInstance(this).play_backgroud(this);




        previous.setOnClickListener(this);
        Restart.setOnClickListener(this);

        DisplayMetrics displayMetrics;
        displayMetrics = getResources().getDisplayMetrics();
        int Width;
        Width = displayMetrics.widthPixels;

       fl.setMinimumHeight(Width-40);
       am_layout.setMinimumHeight(Width-40);
        gv_layout.setMinimumHeight(Width-40);



        View decorView = this.getWindow().getDecorView();
      decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        MainActivity.Myhandler myhandler=new MainActivity.Myhandler(new WeakReference(MainActivity.getMainActivity()));

        layout.setOnTouchListener(new View.OnTouchListener() {
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

                                gv_layout.SwipeLeft();
                                gv_layout.saveLayout();
                                Tool.Step++;
                            }
                            else if (offsetX>5) {
                                // backgoudSound.getInstance(getContext()).play(1);
                                gv_layout.SwipeRight();
                                gv_layout.saveLayout();
                                Tool.Step++;

                            }
                        }else{
                            if(offsetY<-5) {
                                // backgoudSound.getInstance(getContext()).play(1);
                                gv_layout.Swipeup();
                                gv_layout.saveLayout();
                                Tool.Step++;
                            }
                            else if (offsetY>5) {
                                //  backgoudSound.getInstance(getContext()).play(1);
                                gv_layout.Swipedwon();
                                gv_layout.saveLayout();
                                Tool.Step++;

                            }
                        }
                        if(Tool.Step==1)
                            myhandler.sendEmptyMessage(0x123);
                        break;
                    }
                }

                return true;
            }
        });



    }
    public void  game_to_switch(){
        SharedPreferences sp=getSharedPreferences(Tool.Load_or_Star,MODE_PRIVATE);
        for(int i = 0; i< Tool.is_Destory.length; i++) {
            Tool.is_Destory[i] = sp.getBoolean(Tool.Save_Item + i, false);


        }
    }
    public void  initButton(){
        bt_score_tv=findViewById(R.id.BestScore);
        score_tv=findViewById(R.id.Score);
        Restart=findViewById(R.id.restart);
        previous=findViewById(R.id.previous);
        gv_layout=findViewById(R.id.gameView_layout);
        am_layout=findViewById(R.id.anime_layout);
        fl=findViewById(R.id.panel);
        layout=findViewById(R.id.game_layout);
    }

    @Override
    protected void onResume() {
        gv_layout.refresh_music();
        super.onResume();
    }



    public  Anime  getAnimLayer(){
        return am_layout;

    }
    public  static MainActivity getMainActivity(){
        return mainActivity;
    }

    public void addScore(int s){
        SCORE=SCORE+s;
        Log.d("score1",SCORE+"");

        showScore(SCORE);


        if (SCORE>getScore())
        {
         max=SCORE;
         Tool.Save_list_score=true;
        }else max=getScore();
        saveScore(max);
        showBestScore(max);

    }
    public int getreturnScore(){
        return SCORE;
    }
    public void clearScore(){
        SCORE=0;
        showScore(SCORE);
    }
    public void saveScore(int s){
        SharedPreferences.Editor editor=getSharedPreferences(Tool.Save_Layout+ Config.NUM,MODE_PRIVATE).edit();
        editor.putInt(Tool.Save_BestScore,s);

        editor.apply();
    }

    public  int getScore(){
        return getSharedPreferences(Tool.Save_Layout+ Config.NUM,MODE_PRIVATE).getInt(Tool.Save_BestScore,0);
    }


    public void showScore(int s){
        score_tv.setText(s+"");
    }
    public  void showBestScore(int s){
        bt_score_tv.setText(s+"");
    }
    public  void  updata_Score(int s){
        SCORE=s;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.previous:
                if (Tool.Step<1){
                    Log.d("yes",Tool.Step+"");
                    BackgoudSound.getInstance(this).play(5);}
                else{
                    BackgoudSound.getInstance(this).play(6);
                    gv_layout.previous();}
                break;
            case R.id.restart:
                BackgoudSound.getInstance(this).play(6);

             /*  AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this,R.style.AlertDialog);
                builder.setMessage(getResources().getString(R.string.restar))

                        .setIcon(R.color.backgroud)
                        .setPositiveButton(getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                gv_layout.startGame();

                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.deny), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create();
                builder.show();

              */
                new ConfirmDialog(this,true)
                        .setMessageColor(getResources().getColor(R.color.button))
                        .setMessage(getResources().getString(R.string.restar),40f)
                        .setNegativeButton(getResources().getString(R.string.deny), 15f,getResources().getColor(R.color.Textcolor), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.confirm), 15f,getResources().getColor(R.color.Textcolor), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                gv_layout.startGame();
                            }
                        }).create().show();



                break;
        }

    }
   static  class  Myhandler extends Handler{

        private  WeakReference<MainActivity> activity;
        public Myhandler(WeakReference<MainActivity> activity){
            this.activity=activity;

        }
       @Override
       public void handleMessage(@NonNull Message msg) {
           super.handleMessage(msg);
           switch (msg.what){
               case 0x123:
                  // MainActivity.getMainActivity().previous.setEnabled(true);
                   MainActivity.getMainActivity().previous.setBackground(MainActivity.getMainActivity().getResources().getDrawable(R.drawable.bg));
                   break;
               case 0x111:
                 //  MainActivity.getMainActivity().previous.setEnabled(false);
                   MainActivity.getMainActivity().previous.setBackgroundColor(MainActivity.getMainActivity().getResources().getColor(R.color.black));
                   break;

           }

       }
   }


    @Override
    protected void onStop() {
        super.onStop();


    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor1=getSharedPreferences(Tool.Load_or_Star,MODE_PRIVATE).edit();
        Tool.is_Destory[Config.NUM-3]=true;
        for (int i = 0; i< Tool.is_Destory.length; i++)
            editor1.putBoolean(Tool.Save_Item + i, Tool.is_Destory[i]);
        editor1.apply();

        SharedPreferences sp= getSharedPreferences(Tool.Save_Layout+ Config.NUM, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        for (int y = 0; y< Config.NUM; y++) {
            for (int x = 0; x < Config.NUM; x++) {
                editor.putInt(Tool.Save_Item1 + x + y, gv_layout.card_save1[x][y]);
                editor.putInt(Tool.Save_Item + x + y, gv_layout.card_save[x][y]);
            }
        }
        editor.putInt(Tool.Save_Score,gv_layout.score[0]);
        editor.putInt(Tool.Save_Score1,gv_layout.score[1]);
        editor.putInt(Tool.Step_save,Tool.Step);
        editor.apply();


        BackgoudSound.getInstance(this).BG_stop();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
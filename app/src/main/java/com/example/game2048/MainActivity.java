package com.example.game2048;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static MainActivity mainActivity=null;
    private TextView bt_score_tv;
    private  TextView score_tv;
    private ImageButton Restart;
    public   ImageButton previous;
    private  gameView gv_layout;
    private  Anime am_layout;
    private  int SCORE=0;
    private FrameLayout fl;
    private String SAVE_MESSAGE="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        initButton();
        mainActivity=this;
        previous.setOnClickListener(this);
        Restart.setOnClickListener(this);

        previous.setEnabled(false);
        previous.setBackgroundColor(getResources().getColor(R.color.black));


        SAVE_MESSAGE+="layout4";

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





    }
    public void  initButton(){
        bt_score_tv=findViewById(R.id.BestScore);
        score_tv=findViewById(R.id.Score);
        Restart=findViewById(R.id.restart);
        previous=findViewById(R.id.previous);
        gv_layout=findViewById(R.id.gameView_layout);
        am_layout=findViewById(R.id.anime_layout);
        fl=findViewById(R.id.panel);

    }

    public  Anime  getAnimLayer(){
        return am_layout;

    }
    public  static MainActivity getMainActivity(){
        return mainActivity;
    }

    public void addScore(int s){
        SCORE=SCORE+s;

        showScore(SCORE);

        int max= Math.max(SCORE,getScore());
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
        SharedPreferences.Editor editor=getSharedPreferences("save",MODE_PRIVATE).edit();
        editor.putInt(SAVE_MESSAGE,s);

        editor.commit();
    }

    public  int getScore(){
        return getSharedPreferences("save",MODE_PRIVATE).getInt(SAVE_MESSAGE,0);
    }



    public void showScore(int s){
        score_tv.setText(s+"");
    }
    public  void showBestScore(int s){
        bt_score_tv.setText(s+"");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.previous:

                    gv_layout.previous();
                break;
            case R.id.restart:
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("是否重新开始")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                gv_layout.startGame();

                            }
                        })
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create();
                builder.show();
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
           if (msg.what == 0x123){
               Log.d("updataUI","1");
               MainActivity.getMainActivity().previous.setEnabled(true);
               MainActivity.getMainActivity().previous.setBackground(MainActivity.getMainActivity().getResources().getDrawable(R.drawable.bg));
       }
       }
   }


}
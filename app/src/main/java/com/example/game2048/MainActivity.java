package com.example.game2048;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //backgoudSound.getInstance(this);
        game_to_switch();
        setContentView(R.layout.activity_main);
        initButton();
        mainActivity=this;

        if (tool.FLAG_TO_CONTROL_MUSIC)
            backgoudSound.getInstance(this).play_backgroud(this);


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





    }
    public void  game_to_switch(){
        SharedPreferences sp=getSharedPreferences("GAME_SWITCH",MODE_PRIVATE);
        for(int i=0;i<tool.is_Destory.length;i++) {
            tool.is_Destory[i] = sp.getBoolean("Item" + i, false);
            if (tool.is_Destory[i])
            Log.d("isuse",1+""+i);
            else  Log.d("isuse",0+""+i);

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
        SharedPreferences.Editor editor=getSharedPreferences("Layout"+config.NUM,MODE_PRIVATE).edit();
        editor.putInt("BestScore",s);

        editor.apply();
    }

    public  int getScore(){
        return getSharedPreferences("Layout"+config.NUM,MODE_PRIVATE).getInt("BestScore",0);
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
                if (gv_layout.step<1)
                    backgoudSound.getInstance(this).play(5);
                else
                    backgoudSound.getInstance(this).play(6);
                    gv_layout.previous();
                break;
            case R.id.restart:
                backgoudSound.getInstance(this).play(6);
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
        SharedPreferences.Editor editor1=getSharedPreferences("GAME_SWITCH",MODE_PRIVATE).edit();
        tool.is_Destory[config.NUM-3]=true;
        for (int i=0;i<tool.is_Destory.length;i++)
            editor1.putBoolean("Item" + i, tool.is_Destory[i]);
        editor1.apply();

        SharedPreferences sp= getSharedPreferences("Layout"+config.NUM, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        for (int y=0;y<config.NUM;y++) {
            for (int x = 0; x < config.NUM; x++) {
                editor.putInt("Items1" + x + y, gv_layout.card_save1[x][y]);
                editor.putInt("Items" + x + y, gv_layout.card_save[x][y]);
            }
        }
        editor.putInt("score",gv_layout.score[0]);
        editor.putInt("score1",gv_layout.score[1]);
        editor.putInt("step",gv_layout.step);
        editor.apply();
        backgoudSound.getInstance(this).BG_stop();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}
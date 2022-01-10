package com.example.game2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private static MainActivity mainActivity=null;
    private TextView bt_score_tv;
    private  TextView score_tv;
    private ImageButton Restart;
    private  ImageButton previous;
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
        View decorView = this.getWindow().getDecorView();

        SAVE_MESSAGE+="layout4";

        DisplayMetrics displayMetrics;
        displayMetrics = getResources().getDisplayMetrics();
        int cardWidth;
        cardWidth = displayMetrics.widthPixels;

       fl.setMinimumHeight(cardWidth-40);



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

}
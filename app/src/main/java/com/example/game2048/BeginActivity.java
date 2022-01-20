package com.example.game2048;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.widget.ViewSwitcher;

public class BeginActivity extends AppCompatActivity implements View.OnClickListener {


    private ViewFlipper VF;
    private  Button previous;
    private TextSwitcher switcher;
    private  Button next;
    private  Button begin;
    private ImageButton Music_Control;
    private ConstraintLayout layout;

    private final int array[]={3,4,5};
    private  final  int  text[]={R.string.easy,R.string.mediu,R.string.big};
    private int Index=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);
        BackgoudSound.getInstance(BeginActivity.this);
        initButton();
        Config.NUM=array[Index];

        begin.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        Music_Control.setOnClickListener(this);
        BackgoudSound.getInstance(this).preload();

        Tool.FLAG_TO_CONTROL_MUSIC=getSharedPreferences(Tool.Music_Save,MODE_PRIVATE).getBoolean(Tool.Music_play,true);
        if(Tool.FLAG_TO_CONTROL_MUSIC) {
            Music_Control.setBackground(getResources().getDrawable(R.drawable.ic_baseline_music_note_24));
            Tool.FLAG_TO_CONTROL_MUSIC=true;
        }else {
            Music_Control.setBackground(getResources().getDrawable(R.drawable.ic_baseline_music_off_24));
            BackgoudSound.getInstance(this).BG_stop();
        }


        layout.setOnTouchListener(new View.OnTouchListener() {
            float x1,x=0;
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()){

                    case MotionEvent.ACTION_DOWN:
                        x1=motionEvent.getX();

                        break;
                    case MotionEvent.ACTION_UP:{
                        x=motionEvent.getX()-x1;
                      //  Log.d("dec",x1+"");
                        if (x>5)  { previous_page();}
                        else if (x<-5) {next_page();}
                        break;

                }

                }

                return true;
            }
        });





        switcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView tv=new TextView(BeginActivity.this);
                tv.setTextSize(18);
                tv.setGravity(Gravity.CENTER);
                tv.setLinkTextColor(Color.WHITE);
                tv.setText(getResources().getString(Math.abs(text[Index])));
                return tv;
            }
        });

    }
    public  void initButton(){
        VF=findViewById(R.id.select_image);
        previous=findViewById(R.id.select_previous);
        next=findViewById(R.id.select_next);
        begin=findViewById(R.id.begin);
        switcher=findViewById(R.id.tv_show_level);
        Music_Control=findViewById(R.id.imageButton);
        layout=findViewById(R.id.begin_page);

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
       case  R.id.begin:
           BackgoudSound.getInstance(this).play(6);
           Intent intent=new Intent(this,MainActivity.class);


           startActivity(intent);
            break;
            case R.id.select_previous:

                BackgoudSound.getInstance(this).play(6);
                previous_page();

                break;
            case R.id.select_next:
                BackgoudSound.getInstance(this).play(6);
                next_page();
                break;
            case  R.id.imageButton:
                if(Tool.FLAG_TO_CONTROL_MUSIC) {
                    Tool.FLAG_TO_CONTROL_MUSIC=false;
                    Music_Control.setBackground(getResources().getDrawable(R.drawable.ic_baseline_music_off_24));
                    BackgoudSound.getInstance(this).BG_stop();
                }else {
                    Music_Control.setBackground(getResources().getDrawable(R.drawable.ic_baseline_music_note_24));
                    Tool.FLAG_TO_CONTROL_MUSIC=true;
                }
                getSharedPreferences(Tool.Music_play,MODE_PRIVATE).edit().putBoolean(Tool.Music_Save, Tool.FLAG_TO_CONTROL_MUSIC).apply();
        }
    }
    public void next_page(){
        BackgoudSound.getInstance(BeginActivity.this).play(2);
        Index=(Index+1)%array.length;
        Config.NUM=array[Math.abs(Index)];
        VF.setInAnimation(getApplicationContext(),R.anim.animated_left);
       // Log.d("select_R",Index+"");

        switcher.setText(getResources().getString(text[Math.abs(Index)]));
        VF.showNext();
    }
    public  void  previous_page(){
        BackgoudSound.getInstance(BeginActivity.this).play(2);
        if (Index==0) Index=2-array.length;
        Index=Math.abs((Index-1)%array.length);

        Config.NUM=array[Index];
      //  Log.d("select_L",Index+"");
        VF.setInAnimation(getApplicationContext(),R.anim.animate_right);

        switcher.setText(getResources().getString(text[Math.abs(Index)]));
        VF.showPrevious();
    }

    @Override
    protected void onPause() {
        super.onPause();
       // backgoudSound.getInstance(this).release();
    }

}
package com.example.game2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.widget.ViewSwitcher;

public class BEGIN_Activity extends AppCompatActivity implements View.OnClickListener {


    private ViewFlipper VF;
    private  Button previous;
    private TextSwitcher switcher;
    private  Button next;
    private  Button begin;
    private final int array[]={3,4,5};
    private  final  int  text[]={R.string.easy,R.string.mediu,R.string.big};
    private int Index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);
        backgoudSound.getInstance(BEGIN_Activity.this);
        initButton();
        config.NUM=array[Index];

        begin.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);






        switcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView tv=new TextView(BEGIN_Activity.this);
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

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
       case  R.id.begin:
           backgoudSound.getInstance(this).play(6);
           Intent intent=new Intent(this,MainActivity.class);

           startActivity(intent);
            break;
            case R.id.select_previous:
                backgoudSound.getInstance(this).play(6);
                previous_page();
                break;
            case R.id.select_next:
                backgoudSound.getInstance(this).play(6);
                next_page();
                break;
        }
    }
    public void next_page(){
        backgoudSound.getInstance(BEGIN_Activity.this).play(2);
        Index=(Index+1)%array.length;
        config.NUM=array[Math.abs(Index)];
        VF.setInAnimation(getApplicationContext(),R.anim.animated_left);
        Log.d("select_R",Index+"");

        switcher.setText(getResources().getString(text[Math.abs(Index)]));
        VF.showNext();
    }
    public  void  previous_page(){
        backgoudSound.getInstance(BEGIN_Activity.this).play(2);
        if (Index==0) Index=2-array.length;
        Index=Math.abs((Index-1)%array.length);

        config.NUM=array[Index];
        Log.d("select_L",Index+"");
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
package com.example.game2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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
           Intent intent=new Intent(this,MainActivity.class);

           startActivity(intent);
            break;
            case R.id.select_previous:

                Index=(Index-1)%3;
                config.NUM=array[Math.abs(Index)];
                Log.d("select",Index+"");
                VF.setInAnimation(getApplicationContext(),R.anim.animate_right);

                switcher.setText(getResources().getString(text[Math.abs(Index)]));
                VF.showPrevious();
                break;
            case R.id.select_next:
                Index=(Index+1)%3;
                config.NUM=array[Math.abs(Index)];
                VF.setInAnimation(getApplicationContext(),R.anim.animated_left);
                Log.d("select",Index+"");

                switcher.setText(getResources().getString(text[Math.abs(Index)]));
                VF.showNext();
                break;
        }
    }
}
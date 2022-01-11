package com.example.game2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

public class BEGIN_Activity extends AppCompatActivity implements View.OnClickListener {


    private ViewFlipper VF;
    private  Button previous;
    private  Button next;
    private  Button begin;
    private int array[]={3,4,5};
    private int Index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);
        initButton();
        begin.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);

    }
    public  void initButton(){
        VF=findViewById(R.id.select_image);
        previous=findViewById(R.id.select_previous);
        next=findViewById(R.id.select_next);
        begin=findViewById(R.id.begin);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
       case  R.id.begin:
           Intent intent=new Intent(this,MainActivity.class);

           startActivity(intent);
            break;
            case R.id.select_previous:
                Index=Math.abs(Index-1)%3;
                config.NUM=array[Index];
               VF.setInAnimation(getApplicationContext(),R.anim.animated_left);
                VF.showPrevious();
                break;
            case R.id.select_next:
                Index=Math.abs(Index+1)%3;
                config.NUM=array[Index];
             VF.setInAnimation(getApplicationContext(),R.anim.animate_right);
                VF.showNext();
                break;
        }
    }
}
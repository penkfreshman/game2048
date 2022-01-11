package com.example.game2048;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Anime  extends FrameLayout {
    public Anime(@NonNull Context context) {
        super(context);
    }

    public Anime(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Anime(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void initLayer(){
    }
    private List<Card> cards = new ArrayList<Card>();
    public void createMoveAnim(final Card from,final Card to,int fromX,int toX,int fromY,int toY){

        final Card c = getCard(from.getNum());


        LayoutParams lp = new LayoutParams(config.CARD_WIDTH, config.CARD_WIDTH);
        lp.leftMargin = fromX*config.CARD_WIDTH;
        lp.topMargin = fromY*config.CARD_WIDTH;
        c.setLayoutParams(lp);

        if (to.getNum()<=0) {
            to.getLabel().setVisibility(View.INVISIBLE);
        }
        TranslateAnimation ta = new TranslateAnimation(0, config.CARD_WIDTH*(toX-fromX), 0, config.CARD_WIDTH*(toY-fromY));

        ta.setDuration(100);
        ta.setInterpolator(new DecelerateInterpolator());
        ta.setFillEnabled(true);

        ta.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                to.getLabel().setVisibility(View.VISIBLE);
                recycleCard(c);
            }
        });
        c.startAnimation(ta);
    }

    private Card getCard(int num){
        Card c;
        if (cards.size()>0) {
            c = cards.remove(0);
        }else{
            c = new Card(getContext());
            addView(c);
        }
        c.setVisibility(View.VISIBLE);
        c.setNum(num);
        return c;
    }
    private void recycleCard(Card c){
        c.setVisibility(View.INVISIBLE);
        c.setAnimation(null);
        cards.add(c);
    }


    public void createScaleTo1(Card target){
       ScaleAnimation sa = new ScaleAnimation(0.1f, 1, 0.1f, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(100);
        target.setAnimation(null);

        target.getLabel().startAnimation(sa);


    }
}

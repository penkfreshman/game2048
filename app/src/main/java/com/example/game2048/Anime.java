package com.example.game2048;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.plattysoft.leonids.ParticleSystem;

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
        backgoudSound.getInstance(getContext()).play(1);
        target.getLabel().startAnimation(sa);
    }

    public void  create64(Card taget){

      RotateYAnimation rotateXAnimation = new RotateYAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateXAnimation.setDuration(1000);

        rotateXAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                taget.setNum(0);
                taget.getLabel().setBackground(getResources().getDrawable(R.drawable.ic_baseline_star_24));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                taget.setNum(64);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {


            }
        });
        taget.startAnimation(rotateXAnimation);
    }

    public    void   paly_animation(Context context){
        new ParticleSystem(MainActivity.getMainActivity(), 10, R.drawable.star1, 1000)
                .setSpeedModuleAndAngleRange(0.01f, 0.02f, 0, 70)

                .setAcceleration(0f, 80)
                .emit(-50, 100, 5, 5000);

        new ParticleSystem(MainActivity.getMainActivity(), 10, R.drawable.star1, 5000)
                .setSpeedModuleAndAngleRange(0.01f, 0.2f, 0, 70)

                .setAcceleration(0f, 80)
                .emit(-50, 300, 5, 5000);

        new ParticleSystem(MainActivity.getMainActivity(), 10, R.drawable.star1, 5000)
                .setSpeedModuleAndAngleRange(0.01f, 0.2f, 0, 90)

                .setAcceleration(0f, 80)
                .emit(-50,600 , 5, 10000);

        new ParticleSystem(MainActivity.getMainActivity(), 10, R.drawable.star1, 5000)
                .setSpeedModuleAndAngleRange(0.01f, 0.2f, 0, 90)

                .setAcceleration(0f, 80)
                .emit(-50,900 , 5, 5000);

    }
    public   void  play_animation1024(){

        new ParticleSystem(MainActivity.getMainActivity(), 100, R.drawable.bg_1024_anime, 3000)
                .setSpeedModuleAndAngleRange(0.05f, 0.05f, 0, 360)
                .setRotationSpeed(30)
                .setAcceleration(0, 90)
                .emit(600,1000, 300,9000);


    }
}

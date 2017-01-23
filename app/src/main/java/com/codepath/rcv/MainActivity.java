package com.codepath.rcv;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    HorizontalScrollView horizontalScrollView;
    ImageView heartsIv;
    ImageView arrowIv;
    Handler handler;

    AnimationDrawable heartAnimation;
    AnimatorSet arrowAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.scrollview);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startArrowAnimation();
                startHeartsAnimation();
            }
        }, 2000L);
    }


    private void startHeartsAnimation() {
        heartsIv = (ImageView) findViewById(R.id.heartsIv);
        heartsIv.setBackgroundResource(R.drawable.heart_animation);
        // Get the background, which has been compiled to an AnimationDrawable object.
        heartAnimation = (AnimationDrawable) heartsIv.getBackground();

        heartsIv.setVisibility(View.VISIBLE);
        // Start the animation (looped playback by default).
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                heartAnimation.start();
            }
        }, 2000L);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                heartsIv.setVisibility(View.GONE);
            }
        }, 2000 + 3500);
    }

    private void startArrowAnimation() {
        arrowIv = (ImageView) findViewById(R.id.arrowIv);
        int targetX = ((View) arrowIv.getParent()).getWidth() - 50;
        int targetY = ((View) arrowIv.getParent()).getHeight() - 50;
        arrowAnimation = new AnimatorSet();

        ObjectAnimator y = ObjectAnimator.ofFloat(arrowIv,
                View.TRANSLATION_Y, -(targetY - arrowIv.getHeight()));

        ObjectAnimator x = ObjectAnimator.ofFloat(arrowIv,
                View.TRANSLATION_X, targetX - arrowIv.getWidth());

        arrowAnimation.playTogether(x, y);
        arrowAnimation.setInterpolator(new LinearInterpolator());
        arrowAnimation.setDuration(3500);
        arrowAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                arrowIv.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        arrowAnimation.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (heartAnimation != null && heartAnimation.isRunning()) {
            heartAnimation.stop();
        }
        heartsIv.setVisibility(View.GONE);
        if (arrowAnimation != null && arrowAnimation.isRunning()) {
            arrowAnimation.cancel();
            arrowIv.setVisibility(View.GONE);
        }
    }
}

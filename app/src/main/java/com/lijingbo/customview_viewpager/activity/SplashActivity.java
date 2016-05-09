package com.lijingbo.customview_viewpager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.lijingbo.customview_viewpager.R;

public class SplashActivity extends AppCompatActivity {
    private ImageView view_image;
    private boolean first;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        view_image= (ImageView) findViewById(R.id.view_image);
        animationview(view_image);
        first = getSharedPreferences("viewpager", MODE_PRIVATE).getBoolean("first", true);
    }


    private void animationview(View view) {
        AnimationSet set = new AnimationSet(false);
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(alpha);
        set.addAnimation(rotate);
        set.addAnimation(scale);
        set.setDuration(800);
        set.setFillAfter(true);
        view.startAnimation(set);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                goToNextActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void goToNextActivity() {
        if ( first ) {
            startActivity(new Intent(SplashActivity.this, GuideActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }
        finish();
    }

}

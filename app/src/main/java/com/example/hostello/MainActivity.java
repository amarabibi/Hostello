package com.example.hostello;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView logo;
    private TextView tagline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = findViewById(R.id.hostello_logo);
        tagline = findViewById(R.id.tagline);

        // Start Haiku-style animation
        animateHaiku();

        // Navigate to Home after splash
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, MainHomeActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }, 2800); // Calm timing
    }

    // 🌿 Haiku Animation: soft, minimal, poetic
    private void animateHaiku() {

        // Initial state
        logo.setAlpha(0f);
        logo.setScaleX(0.95f);
        logo.setScaleY(0.95f);
        logo.setTranslationY(20f);

        tagline.setAlpha(0f);
        tagline.setTranslationY(15f);

        // Logo fade + gentle scale + slight rise
        ObjectAnimator logoAlpha =
                ObjectAnimator.ofFloat(logo, "alpha", 0f, 1f);
        ObjectAnimator logoScaleX =
                ObjectAnimator.ofFloat(logo, "scaleX", 0.95f, 1f);
        ObjectAnimator logoScaleY =
                ObjectAnimator.ofFloat(logo, "scaleY", 0.95f, 1f);
        ObjectAnimator logoTranslateY =
                ObjectAnimator.ofFloat(logo, "translationY", 20f, 0f);

        logoAlpha.setDuration(1200);
        logoScaleX.setDuration(1200);
        logoScaleY.setDuration(1200);
        logoTranslateY.setDuration(1200);

        AnimatorSet logoSet = new AnimatorSet();
        logoSet.playTogether(
                logoAlpha,
                logoScaleX,
                logoScaleY,
                logoTranslateY
        );
        logoSet.setInterpolator(new DecelerateInterpolator());
        logoSet.start();

        // Tagline appears after a short pause
        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            ObjectAnimator taglineAlpha =
                    ObjectAnimator.ofFloat(tagline, "alpha", 0f, 1f);
            ObjectAnimator taglineTranslateY =
                    ObjectAnimator.ofFloat(tagline, "translationY", 15f, 0f);

            taglineAlpha.setDuration(1000);
            taglineTranslateY.setDuration(1000);

            AnimatorSet taglineSet = new AnimatorSet();
            taglineSet.playTogether(taglineAlpha, taglineTranslateY);
            taglineSet.setInterpolator(new AccelerateDecelerateInterpolator());
            taglineSet.start();

        }, 800);
    }
}

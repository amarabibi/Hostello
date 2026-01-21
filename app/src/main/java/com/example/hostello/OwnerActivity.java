package com.example.hostello;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class OwnerActivity extends AppCompatActivity {

    private View logo, contentLayout;
    private MaterialButton getStartedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);

        logo = findViewById(R.id.logo);
        contentLayout = findViewById(R.id.content_layout);
        getStartedButton = findViewById(R.id.get_started_button_owner);

        // Animate logo (fade + slide down)
        logo.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(800)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .withEndAction(() -> {
                    // Animate content layout
                    contentLayout.animate()
                            .alpha(1f)
                            .translationY(0f)
                            .setDuration(800)
                            .setInterpolator(new AccelerateDecelerateInterpolator())
                            .start();
                    // Animate Get Started button
                    getStartedButton.animate()
                            .alpha(1f)
                            .translationY(0f)
                            .setDuration(800)
                            .setStartDelay(400)
                            .start();
                }).start();

        // Back button
        findViewById(R.id.back_button).setOnClickListener(v -> finish());

        // Navigate to next screen
        getStartedButton.setOnClickListener(v -> {
            Intent intent = new Intent(OwnerActivity.this, OwnerRegistrationActivity.class);
            startActivity(intent);
        });
    }
}

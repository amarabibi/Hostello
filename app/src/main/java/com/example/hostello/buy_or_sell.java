package com.example.hostello;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

public class buy_or_sell extends AppCompatActivity {

    private MaterialCardView ownerButton;
    private MaterialCardView memberButton;
    private TextView ownerText;
    private TextView memberText;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buy_or_sell);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        setupClickListeners();
        setupHoverEffects();
        setupBackPressHandler();
    }

    private void initializeViews() {
        backButton = findViewById(R.id.back_button);
        ownerButton = findViewById(R.id.owner_button);
        memberButton = findViewById(R.id.member_button);
        ownerText = findViewById(R.id.owner_text);
        memberText = findViewById(R.id.member_text);
    }

    private void setupClickListeners() {
        backButton.setOnClickListener(v -> finish());

        ownerButton.setOnClickListener(v -> {
            Intent intent = new Intent(buy_or_sell.this, OwnerActivity.class);
            startActivity(intent);
        });

        memberButton.setOnClickListener(v -> {
            Intent intent = new Intent(buy_or_sell.this, PhoneNumber.class);
            startActivity(intent);
        });
    }

    private void setupHoverEffects() {
        setupButtonHover(ownerButton, ownerText);
        setupButtonHover(memberButton, memberText);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupButtonHover(MaterialCardView button, TextView textView) {
        button.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // Fill with blue on press
                    button.setCardBackgroundColor(Color.parseColor("#1565C0"));
                    textView.setTextColor(Color.WHITE);
                    v.performClick(); // Add this for accessibility
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    // Return to white on release
                    button.setCardBackgroundColor(Color.WHITE);
                    textView.setTextColor(Color.parseColor("#1565C0"));
                    break;
            }
            return false; // Return false to allow click events to process
        });
    }

    private void setupBackPressHandler() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }
}
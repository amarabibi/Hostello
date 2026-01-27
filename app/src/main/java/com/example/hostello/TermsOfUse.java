package com.example.hostello;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class TermsOfUse extends AppCompatActivity {

    private CheckBox termsCheckbox;
    private CardView agreeBtn, declineBtn;
    private ScrollView scrollView;
    private TextView scrollIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_use);

        // Initialize Views
        termsCheckbox = findViewById(R.id.terms_checkbox);
        agreeBtn = findViewById(R.id.term_agree_btn);
        declineBtn = findViewById(R.id.terms_back_btn);
        scrollView = findViewById(R.id.terms_scroll_view);
        scrollIndicator = findViewById(R.id.scroll_indicator);

        // Back Button
        findViewById(R.id.back).setOnClickListener(v -> finish());

        // Scroll listener to hide the indicator when user reaches bottom
        scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
            int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));

            if (diff <= 0) {
                scrollIndicator.setVisibility(View.GONE);
            }
        });

        // Decline Button
        declineBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Terms Declined", Toast.LENGTH_SHORT).show();
            finish();
        });

        // Agree Button Logic
        agreeBtn.setOnClickListener(v -> {
            if (termsCheckbox.isChecked()) {
                // Save acceptance state or move to next screen
                Toast.makeText(this, "Terms Accepted!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Please check the agreement box first", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
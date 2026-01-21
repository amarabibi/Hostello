package com.example.hostello;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TermsOfUse extends AppCompatActivity {

    private static final String TAG = "TermsOfUse";

    private CheckBox termsCheckbox;
    private CardView agreeButton;
    private TextView scrollIndicator;
    private ScrollView scrollView;

    private boolean hasScrolledToBottom = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_terms_of_use);

        // Edge-to-Edge padding
        final android.view.View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        } else {
            Log.e(TAG, "Main view not found in layout");
        }

        setupViews();
        setupScrollListener();
        setupBackPressHandler();
    }

    private void setupViews() {
        ImageButton backButton = findViewById(R.id.back);
        CardView declineButton = findViewById(R.id.terms_back_btn);
        agreeButton = findViewById(R.id.term_agree_btn);
        termsCheckbox = findViewById(R.id.terms_checkbox);

        scrollView = findViewById(R.id.terms_scroll_view);

        // Disable checkbox until scrolled
        if (termsCheckbox != null) {
            termsCheckbox.setEnabled(false);
            termsCheckbox.setChecked(false);
        }

        // Disable agree button initially
        updateAgreeButtonState(false);

        // Back button
        if (backButton != null) backButton.setOnClickListener(v -> finish());
        else Log.e(TAG, "Back button not found");

        // Decline button
        if (declineButton != null) declineButton.setOnClickListener(v -> finish());
        else Log.e(TAG, "Decline button not found");

        // Checkbox listener
        if (termsCheckbox != null) {
            termsCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> updateAgreeButtonState(isChecked));
        }

        // Agree button
        if (agreeButton != null) {
            agreeButton.setOnClickListener(v -> {
                if (termsCheckbox != null && termsCheckbox.isChecked()) {
                    goToHome();
                } else {
                    Toast.makeText(this, "Please accept the terms to continue", Toast.LENGTH_SHORT).show();
                }
            });
        } else Log.e(TAG, "Agree button not found");
    }

    private void setupScrollListener() {
        if (scrollView == null || scrollIndicator == null || termsCheckbox == null) return;

        scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            android.view.View contentView = scrollView.getChildAt(0);
            if (contentView == null) return;

            int diff = (contentView.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));

            if (diff <= 50 && !hasScrolledToBottom) { // reached bottom
                hasScrolledToBottom = true;
                scrollIndicator.setVisibility(android.view.View.GONE);
                termsCheckbox.setEnabled(true);
                Toast.makeText(this, "You can now accept the terms", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateAgreeButtonState(boolean enabled) {
        if (agreeButton != null) {
            agreeButton.setAlpha(enabled ? 1.0f : 0.5f);
            agreeButton.setEnabled(enabled);
        }
    }

    private void setupBackPressHandler() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }

    private void goToHome() {
        Intent intent = new Intent(TermsOfUse.this, MainHomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

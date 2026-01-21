package com.example.hostello;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;

public class PhoneNumber extends AppCompatActivity {

    private TextInputEditText phoneInput;
    private CardView btnContinue;
    private ImageButton backButton;
    private TextView phoneAgreeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);

        // Initialize views
        phoneInput = findViewById(R.id.phone_input);
        btnContinue = findViewById(R.id.phone_to_otp_btn);
        backButton = findViewById(R.id.back);
        phoneAgreeText = findViewById(R.id.phone_continue_agree);

        // ✅ Continue button click → go to OTP
        btnContinue.setOnClickListener(v -> {
            String phone = phoneInput.getText().toString().trim();

            if (phone.isEmpty()) {
                phoneInput.setError("Enter phone number");
                phoneInput.requestFocus();
                return;
            }

            // Navigate to OTP Activity
            Intent intent = new Intent(PhoneNumber.this, OtpActivity.class);
            intent.putExtra("phone_number", phone);
            startActivity(intent);
        });

        // ✅ Back button click → finish activity
        backButton.setOnClickListener(v -> finish());

        // Optional: clickable terms text
        phoneAgreeText.setOnClickListener(v -> {
            Intent intent = new Intent(PhoneNumber.this, TermsOfUse.class);
            startActivity(intent);
        });
    }
}

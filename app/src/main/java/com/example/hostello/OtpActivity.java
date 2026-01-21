package com.example.hostello;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OtpActivity extends AppCompatActivity {

    private static final String TAG = "OtpActivity";
    private EditText otpInput;
    private CardView confirmButton;
    private ImageButton backButton;
    private TextView resendCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        // Retrieve phone number from intent
        String phoneNumber = getIntent().getStringExtra("phone_number");

        // Edge-to-edge padding
        android.view.View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        // Initialize Views
        otpInput = findViewById(R.id.member_four_digit_input);
        confirmButton = findViewById(R.id.member_confirm);
        backButton = findViewById(R.id.back);
        resendCode = findViewById(R.id.code_confirm_agree);

        // Validate views exist before setting up listeners
        if (otpInput == null || confirmButton == null || backButton == null || resendCode == null) {
            Toast.makeText(this, "Views not found! Check XML IDs.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Back button
        backButton.setOnClickListener(v -> finish());

        // Confirm OTP
        confirmButton.setOnClickListener(v -> {
            Log.d(TAG, "Confirm OTP button clicked");
            String otp = otpInput.getText().toString().trim();
            
            // Validate OTP: should be 4 or 6 digits (matching XML maxLength="6")
            if (TextUtils.isEmpty(otp)) {
                otpInput.setError("Please enter OTP");
                otpInput.requestFocus();
                return;
            }
            
            if (otp.length() != 4 && otp.length() != 6) {
                otpInput.setError("OTP must be 4 or 6 digits");
                otpInput.requestFocus();
                return;
            }

            Log.d(TAG, "OTP validated successfully: " + otp);
            
            // ✅ Navigate to TermsOfUse
            try {
                Intent intent = new Intent(OtpActivity.this, TermsOfUse.class);
                if (phoneNumber != null) {
                    intent.putExtra("phone_number", phoneNumber);
                }
                Log.d(TAG, "Starting TermsOfUse activity...");
                startActivity(intent);
                Log.d(TAG, "TermsOfUse activity started successfully");
            } catch (Exception e) {
                Log.e(TAG, "Error starting TermsOfUse activity", e);
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });

        // Resend OTP
        resendCode.setOnClickListener(v ->
                Toast.makeText(this, "OTP resent", Toast.LENGTH_SHORT).show()
        );
    }
}

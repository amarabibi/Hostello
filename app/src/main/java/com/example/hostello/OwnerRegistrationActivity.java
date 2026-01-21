package com.example.hostello;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;

public class OwnerRegistrationActivity extends AppCompatActivity {

    private ImageButton backButton;
    private TextInputEditText fullNameInput;
    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private CheckBox termsCheckbox;
    private CardView registerButton;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_registration);

        backButton = findViewById(R.id.back_button);
        fullNameInput = findViewById(R.id.full_name_input);
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        termsCheckbox = findViewById(R.id.terms_checkbox);
        registerButton = findViewById(R.id.register_button);

        backButton.setOnClickListener(v -> finish());

        registerButton.setOnClickListener(v -> handleRegistration());
    }

    private void handleRegistration() {

        String fullName = fullNameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (TextUtils.isEmpty(fullName)) {
            fullNameInput.setError("Full name is required");
            fullNameInput.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            emailInput.setError("Email is required");
            emailInput.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Enter a valid email");
            emailInput.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordInput.setError("Password is required");
            passwordInput.requestFocus();
            return;
        }

        if (password.length() < 6) {
            passwordInput.setError("Password must be at least 6 characters");
            passwordInput.requestFocus();
            return;
        }

        if (!termsCheckbox.isChecked()) {
            Toast.makeText(this, "Please accept Terms & Conditions", Toast.LENGTH_SHORT).show();
            return;
        }

        // ✅ SUCCESS → MOVE TO DOCUMENT PAGE
        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(
                OwnerRegistrationActivity.this,
                OwnerDocumentSubmissionActivity.class
        );

        startActivity(intent);
        finish();
    }
}

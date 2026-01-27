package com.example.hostello;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OwnerRegistrationActivity extends AppCompatActivity {

    private AppDatabase db;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_registration);

        db = AppDatabase.getInstance(this);

        // All variables are now TextInputEditText to match the XML tags
        TextInputEditText etName = findViewById(R.id.etName);
        TextInputEditText etPrice = findViewById(R.id.etPrice);
        TextInputEditText etLocation = findViewById(R.id.etLocation);
        TextInputEditText etType = findViewById(R.id.etType);
        TextInputEditText etRoomType = findViewById(R.id.etRoomType);
        TextInputEditText etAmenity1 = findViewById(R.id.etAmenity1);
        TextInputEditText etAmenity2 = findViewById(R.id.etAmenity2);
        TextInputEditText etFacilities = findViewById(R.id.etFacilities);
        TextInputEditText etPhone = findViewById(R.id.etPhone);
        TextInputEditText etEmail = findViewById(R.id.etEmail);
        MaterialButton btnSave = findViewById(R.id.btnSaveHostel);

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String price = etPrice.getText().toString().trim();
            String location = etLocation.getText().toString().trim();
            String type = etType.getText().toString().trim();
            String roomType = etRoomType.getText().toString().trim();
            String a1 = etAmenity1.getText().toString().trim();
            String a2 = etAmenity2.getText().toString().trim();
            String facilities = etFacilities.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            if (name.isEmpty() || price.isEmpty() || location.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Please fill required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Using the 16-parameter constructor from your Hostel.java
            Hostel newHostel = new Hostel(
                    name, price, "5.0", type, location,
                    a1, a2, "Laundry", roomType, "Available",
                    facilities, "Yes", "0", phone, email, "hostel54"
            );

            executor.execute(() -> {
                db.hostelDao().insertAll(newHostel);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Hostel Listed Successfully!", Toast.LENGTH_LONG).show();

                    // Switch to Dashboard
                    Intent intent = new Intent(OwnerRegistrationActivity.this, OwnerDashboardActivity.class);
                    startActivity(intent);
                    finish(); // Close the registration form
                });
            });
        });
    }
}
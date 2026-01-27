package com.example.hostello;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

        try {
            db = AppDatabase.getInstance(this);
        } catch (Exception e) {
            Log.e("HOSTELLO", "Database Initialization Failed: " + e.getMessage());
            Toast.makeText(this, "Database Error. Please restart the app.", Toast.LENGTH_LONG).show();
        }

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

            // Creating the object
            Hostel newHostel = new Hostel(
                    name, price, "5.0", type, location,
                    a1, a2, "Laundry", roomType, "Available",
                    facilities, "Yes", "0", phone, email, "hostel54"
            );

            // Using a more robust execution block
            executor.execute(() -> {
                try {
                    Log.d("HOSTELLO", "Attempting Insert...");

                    // Verify DAO is not null
                    if (db.hostelDao() == null) {
                        throw new Exception("DAO is null. Check Database configuration.");
                    }

                    db.hostelDao().insertHostel(newHostel);
                    Log.d("HOSTELLO", "Insert Complete!");

                    runOnUiThread(() -> {
                        Toast.makeText(OwnerRegistrationActivity.this, "Hostel Listed Successfully!", Toast.LENGTH_LONG).show();

                        // Explicitly using the Activity Class Context
                        Intent intent = new Intent(OwnerRegistrationActivity.this, OwnerDocumentSubmissionActivity.class);
                        // Adding flags to ensure a clean navigation stack
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    });

                } catch (Exception e) {
                    Log.e("HOSTELLO", "CRASH IN EXECUTOR: " + e.getMessage(), e);
                    runOnUiThread(() -> {
                        Toast.makeText(OwnerRegistrationActivity.this, "Save Failed: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    });
                }
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown(); // Prevent memory leaks
    }
}
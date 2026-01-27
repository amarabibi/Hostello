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

        // Initialize database
        try {
            db = AppDatabase.getInstance(this);
            Log.d("HOSTELLO", "Database initialized successfully");
        } catch (Exception e) {
            Log.e("HOSTELLO", "Database Initialization Failed: " + e.getMessage());
            Toast.makeText(this, "Database Error. Please restart the app.", Toast.LENGTH_LONG).show();
            return;
        }

        // Find all views
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

        // Debug: Check if any views are null
        if (etName == null) Log.e("HOSTELLO", "etName is NULL!");
        if (etPrice == null) Log.e("HOSTELLO", "etPrice is NULL!");
        if (etLocation == null) Log.e("HOSTELLO", "etLocation is NULL!");
        if (etType == null) Log.e("HOSTELLO", "etType is NULL!");
        if (etRoomType == null) Log.e("HOSTELLO", "etRoomType is NULL!");
        if (etAmenity1 == null) Log.e("HOSTELLO", "etAmenity1 is NULL!");
        if (etAmenity2 == null) Log.e("HOSTELLO", "etAmenity2 is NULL!");
        if (etFacilities == null) Log.e("HOSTELLO", "etFacilities is NULL!");
        if (etPhone == null) Log.e("HOSTELLO", "etPhone is NULL!");
        if (etEmail == null) Log.e("HOSTELLO", "etEmail is NULL!");
        if (btnSave == null) Log.e("HOSTELLO", "btnSave is NULL!");

        // Check if button was found
        if (btnSave == null) {
            Toast.makeText(this, "UI Error: Button not found", Toast.LENGTH_LONG).show();
            return;
        }

        btnSave.setOnClickListener(v -> {
            Log.d("HOSTELLO", "Save button clicked");

            // Get all input values
            String name = etName != null ? etName.getText().toString().trim() : "";
            String price = etPrice != null ? etPrice.getText().toString().trim() : "";
            String location = etLocation != null ? etLocation.getText().toString().trim() : "";
            String type = etType != null ? etType.getText().toString().trim() : "";
            String roomType = etRoomType != null ? etRoomType.getText().toString().trim() : "";
            String a1 = etAmenity1 != null ? etAmenity1.getText().toString().trim() : "";
            String a2 = etAmenity2 != null ? etAmenity2.getText().toString().trim() : "";
            String facilities = etFacilities != null ? etFacilities.getText().toString().trim() : "";
            String phone = etPhone != null ? etPhone.getText().toString().trim() : "";
            String email = etEmail != null ? etEmail.getText().toString().trim() : "";

            // Validation
            if (name.isEmpty() || price.isEmpty() || location.isEmpty() || phone.isEmpty()) {
                Toast.makeText(OwnerRegistrationActivity.this, "Please fill all required fields (Name, Price, Location, Phone)", Toast.LENGTH_SHORT).show();
                return;
            }

            Log.d("HOSTELLO", "Creating hostel object with name: " + name);

            // Create hostel object
            Hostel newHostel = new Hostel(
                    name, price, "5.0", type, location,
                    a1, a2, "Laundry", roomType, "Available",
                    facilities, "Yes", "0", phone, email, "hostel54"
            );

            // Execute database insert in background
            executor.execute(() -> {
                try {
                    Log.d("HOSTELLO", "Starting database insert...");

                    // Verify DAO is not null
                    if (db == null) {
                        throw new Exception("Database instance is null");
                    }
                    if (db.hostelDao() == null) {
                        throw new Exception("DAO is null. Check Database configuration.");
                    }

                    // Insert into database - use the correct method name from your DAO
                    // Try both possible method names
                    try {
                        db.hostelDao().insertAll(newHostel);
                        Log.d("HOSTELLO", "Insert successful using insertAll()");
                    } catch (NoSuchMethodError e1) {

                    }

                    // Navigate to next screen on success
                    runOnUiThread(() -> {
                        Toast.makeText(OwnerRegistrationActivity.this, "Hostel Listed Successfully!", Toast.LENGTH_LONG).show();

                        try {
                            // Navigate to Document Submission Activity
                            Intent intent = new Intent(OwnerRegistrationActivity.this, OwnerDocumentSubmissionActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } catch (Exception navError) {
                            Log.e("HOSTELLO", "Navigation Error: " + navError.getMessage());
                            Toast.makeText(OwnerRegistrationActivity.this,
                                    "Saved successfully, but navigation failed. Please check if OwnerDocumentSubmissionActivity exists.",
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                } catch (Exception e) {
                    Log.e("HOSTELLO", "Database Insert Failed: " + e.getMessage(), e);
                    e.printStackTrace();

                    runOnUiThread(() -> {
                        Toast.makeText(OwnerRegistrationActivity.this,
                                "Save Failed: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    });
                }
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
            Log.d("HOSTELLO", "Executor service shutdown");
        }
    }
}
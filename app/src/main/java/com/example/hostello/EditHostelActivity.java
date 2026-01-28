package com.example.hostello;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EditHostelActivity extends AppCompatActivity {

    private TextInputEditText etName, etLocation, etPrice, etDescription;
    private MaterialButton btnSave;
    private AppDatabase db;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private int hostelId = -1;
    private Hostel currentHostel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hostel);

        db = AppDatabase.getInstance(this);

        // Initialize Views
        etName = findViewById(R.id.etHostelName);
        etLocation = findViewById(R.id.etHostelLocation);
        etPrice = findViewById(R.id.etHostelPrice);
        etDescription = findViewById(R.id.etHostelDescription);
        btnSave = findViewById(R.id.btnSaveChanges);

        // Get Data from Intent
        hostelId = getIntent().getIntExtra("HOSTEL_ID", -1);

        if (hostelId != -1) {
            loadHostelData(hostelId);
        }

        btnSave.setOnClickListener(v -> saveChanges());
    }

    private void loadHostelData(int id) {
        executor.execute(() -> {
            // Fetch the hostel from the database
            currentHostel = db.hostelDao().getHostelById(id);
            if (currentHostel != null) {
                runOnUiThread(() -> {
                    // Mapping currentHostel fields to the UI
                    etName.setText(currentHostel.name);
                    etLocation.setText(currentHostel.location);
                    etPrice.setText(currentHostel.price);
                    // Mapping facilities to the description field
                    etDescription.setText(currentHostel.facilities);
                });
            }
        });
    }

    private void saveChanges() {
        String name = etName.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String price = etPrice.getText().toString().trim();
        String facilities = etDescription.getText().toString().trim();

        if (name.isEmpty() || location.isEmpty() || price.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        executor.execute(() -> {
            if (currentHostel != null) {
                // Update the currentHostel object with new values from UI
                currentHostel.name = name;
                currentHostel.location = location;
                currentHostel.price = price;
                currentHostel.facilities = facilities;

                // Updated method name to match HostelDao interface: updateHostel instead of update
                db.hostelDao().updateHostel(currentHostel);

                runOnUiThread(() -> {
                    Toast.makeText(this, "Hostel updated successfully!", Toast.LENGTH_SHORT).show();
                    finish(); // Go back to the dashboard
                });
            } else {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Error: Hostel not found", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executor != null) {
            executor.shutdown();
        }
    }
}
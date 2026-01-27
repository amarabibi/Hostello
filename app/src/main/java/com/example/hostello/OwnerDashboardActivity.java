package com.example.hostello;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OwnerDashboardActivity extends AppCompatActivity {

    private AppDatabase db;
    private TextView tvListingCount;
    private MaterialButton btnEdit, btnViewBookings, btnLogout, btnViewHome;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_dashboard);

        db = AppDatabase.getInstance(this);

        // Initialize Views
        tvListingCount = findViewById(R.id.tvListingCount);
        btnEdit = findViewById(R.id.btnEditHostel);
        btnViewBookings = findViewById(R.id.btnViewBookings);
        btnLogout = findViewById(R.id.btnLogoutOwner);
        btnViewHome = findViewById(R.id.btnViewHome); // Initialized properly

        // Fetch stats
        updateDashboardStats();

        // 1. Navigation to Home Fragment (MainActivity)
        btnViewHome.setOnClickListener(v -> {
            Intent intent = new Intent(OwnerDashboardActivity.this, MainHomeActivity.class);
            // FLAG_ACTIVITY_REORDER_TO_FRONT is good,
            // but FLAG_ACTIVITY_CLEAR_TOP is safer if you want a fresh Home view
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });

        // 2. Edit Feature
        btnEdit.setOnClickListener(v -> {
            Toast.makeText(this, "Edit feature coming soon!", Toast.LENGTH_SHORT).show();
        });

        // 3. View Bookings
        btnViewBookings.setOnClickListener(v -> {
            Intent intent = new Intent(this, ManageBookingsActivity.class);
            startActivity(intent);
        });

        // 4. Logout
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish(); // Ensure dashboard is removed from memory
        });
    }

    private void updateDashboardStats() {
        executor.execute(() -> {
            try {
                if (db.hostelDao() != null) {
                    // Fetch data in background
                    List<Hostel> hostels = db.hostelDao().getAllHostels();
                    int count = (hostels != null) ? hostels.size() : 0;

                    // Update UI safely
                    runOnUiThread(() -> {
                        if (!isFinishing()) { // Check if activity is still alive
                            tvListingCount.setText(String.valueOf(count));
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown(); // Clean up thread to prevent memory leaks
    }
}
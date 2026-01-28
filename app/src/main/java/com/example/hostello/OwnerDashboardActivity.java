package com.example.hostello;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OwnerDashboardActivity extends AppCompatActivity {

    private AppDatabase db;
    private TextView tvListingCount, tvWelcome;
    // Updated types to match the new XML layout (Cards instead of Buttons for grid items)
    private View btnEdit, btnViewBookings, btnViewHome;
    private MaterialButton btnLogout;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_dashboard);

        db = AppDatabase.getInstance(this);

        // Initialize Views
        // Note: usage of View or MaterialCardView avoids ClassCastException since these are Cards in the XML now
        tvListingCount = findViewById(R.id.tvListingCount);
        tvWelcome = findViewById(R.id.welcomeText);

        btnEdit = findViewById(R.id.btnEditHostel);
        btnViewBookings = findViewById(R.id.btnViewBookings);
        btnViewHome = findViewById(R.id.btnViewHome);
        btnLogout = findViewById(R.id.btnLogoutOwner);

        // Set dynamic welcome message if user data is available
        // tvWelcome.setText("Hello, " + currentUser.getName());

        // Fetch stats
        updateDashboardStats();

        // 1. Navigation to Home Fragment (MainActivity / MainHomeActivity)
        btnViewHome.setOnClickListener(v -> {
            Intent intent = new Intent(OwnerDashboardActivity.this, MainHomeActivity.class);
            // Brings existing home activity to front rather than recreating it
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });

        // 2. Edit Feature
        btnEdit.setOnClickListener(v -> {
             Intent intent = new Intent(OwnerDashboardActivity.this, EditHostelActivity.class);
             startActivity(intent);
        });

        // 3. View Bookings
        btnViewBookings.setOnClickListener(v -> {
            Intent intent = new Intent(this, ManageBookingsActivity.class);
            startActivity(intent);
        });

        // 4. Logout
        btnLogout.setOnClickListener(v -> {
            // Clear session data here if you have SharedPrefs or Singleton User
            // SessionManager.clear();

            Intent intent = new Intent(this, MainHomeActivity.class);
            // Clear back stack so user can't press back to return to dashboard
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void updateDashboardStats() {
        executor.execute(() -> {
            try {
                if (db != null && db.hostelDao() != null) {
                    // Fetch data in background
                    List<Hostel> hostels = db.hostelDao().getAllHostels();
                    int count = (hostels != null) ? hostels.size() : 0;

                    // You might also fetch total views or bookings count here if you have those Tables
                    // int views = db.statsDao().getTotalViews();

                    // Update UI safely
                    runOnUiThread(() -> {
                        if (!isFinishing() && tvListingCount != null) {
                            tvListingCount.setText(String.valueOf(count));
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    if (!isFinishing()) {
                        Toast.makeText(OwnerDashboardActivity.this, "Error loading stats", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh stats when returning to the dashboard (e.g. after editing a hostel)
        updateDashboardStats();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown(); // Clean up thread to prevent memory leaks
        }
    }
}
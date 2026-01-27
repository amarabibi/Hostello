package com.example.hostello;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OwnerDashboardActivity extends AppCompatActivity {

    private AppDatabase db;
    private TextView tvListingCount;
    // 🔹 Added missing declarations here
    private MaterialButton btnEdit, btnViewBookings, btnLogout;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_dashboard);

        db = AppDatabase.getInstance(this);

        // 🔹 Initialize all views
        tvListingCount = findViewById(R.id.tvListingCount);
        btnEdit = findViewById(R.id.btnEditHostel);
        btnViewBookings = findViewById(R.id.btnViewBookings); // This was missing!
        btnLogout = findViewById(R.id.btnLogoutOwner);

        // Fetch stats
        updateDashboardStats();
// Inside onCreate...
        MaterialButton btnViewHome = findViewById(R.id.btnViewHome);

        btnViewHome.setOnClickListener(v -> {
            // This takes the owner to the main screen where HomeFragment is hosted
            Intent intent = new Intent(OwnerDashboardActivity.this, MainHomeActivity.class);

            // We use these flags so that pressing 'back' doesn't just loop between dashboard and home
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });
        // 🔹 Set Click Listeners
        btnEdit.setOnClickListener(v -> {
            Toast.makeText(this, "Edit feature coming soon!", Toast.LENGTH_SHORT).show();
        });

        btnViewBookings.setOnClickListener(v -> {
            // Navigate to the Manage Bookings screen we created
            Intent intent = new Intent(this, ManageBookingsActivity.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    private void updateDashboardStats() {
        executor.execute(() -> {
            if (db.hostelDao() != null) {
                int count = db.hostelDao().getAllHostels().size();
                runOnUiThread(() -> tvListingCount.setText(String.valueOf(count)));
            }
        });
    }
}
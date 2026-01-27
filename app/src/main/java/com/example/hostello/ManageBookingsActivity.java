package com.example.hostello;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ManageBookingsActivity extends AppCompatActivity {

    private RecyclerView rvBookings;
    private AppDatabase db;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bookings);

        db = AppDatabase.getInstance(this);
        rvBookings = findViewById(R.id.rvBookings);
        rvBookings.setLayoutManager(new LinearLayoutManager(this));

        loadBookings();
    }

    private void loadBookings() {
        executor.execute(() -> {
            List<Booking> bookingList = db.hostelDao().getAllBookings();
            runOnUiThread(() -> {
                // You will need to create a simple BookingAdapter similar to HostelAdapter
                // BookingAdapter adapter = new BookingAdapter(bookingList);
                // rvBookings.setAdapter(adapter);
            });
        });
    }
}